package lk.apiit.eea1.online_crafts_store.CraftItem.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.RoleName;
import lk.apiit.eea1.online_crafts_store.Auth.Repository.CraftCreatorRepository;
import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import lk.apiit.eea1.online_crafts_store.Cart.Entity.Cart;
import lk.apiit.eea1.online_crafts_store.CraftItem.DTO.CreatorCraftOrderDTO;
import lk.apiit.eea1.online_crafts_store.CraftItem.DTO.ItemDTO;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftCreatorCraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftCreatorItemRepository;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftItemRepository;
import lk.apiit.eea1.online_crafts_store.Notifications.Entity.Notifications;
import lk.apiit.eea1.online_crafts_store.Notifications.Repository.NotificationsRepository;
import lk.apiit.eea1.online_crafts_store.Order.Entity.OrderCraftItem;
import lk.apiit.eea1.online_crafts_store.Order.Repository.OrderCraftItemRepository;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CraftItemService {

    @Autowired
    CraftItemRepository craftItemRepository;

    @Autowired
    CraftCreatorItemRepository craftCreatorItemRepository;

    @Autowired
    CraftCreatorRepository craftCreatorRepository;

    @Autowired
    OrderCraftItemRepository orderCraftItemRepository;

    @Autowired
    NotificationsRepository notificationsRepository;

    //only for admins and craft creators
    @Transactional    //because craft item and images should be saved at the same time, rollback if there is any problem
    public String addItem(ItemDTO dto,MultipartFile imgFile) throws IOException {
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //do the role based access to this api if only needed
        String ret="";
        if(userSession.getRole().getRoleName().equals(RoleName.ROLE_CRAFT_CREATOR)){
            ModelMapper modelMapper = new ModelMapper();
            CraftItem craftItem=modelMapper.map(dto,CraftItem.class);
            craftItem.setAvailabilityStatus(true);

            CraftCreator craftCreator=craftCreatorRepository.findByUser_Id(userSession.getId());

            CraftCreatorCraftItem craftCreatorCraftItem=new CraftCreatorCraftItem();
            craftCreatorCraftItem.setCraftCreator(craftCreator);
            craftCreatorCraftItem.setCraftItem(craftItem);

//            System.out.println(dto.getImgFile().getName()+dto.getImgFile().getContentType());
            craftItem.setImgFile(imgFile.getBytes());

            //save images in another table since there are many for one craft item

            craftItemRepository.save(craftItem);
            craftCreatorItemRepository.save(craftCreatorCraftItem);
        }
        else{ //customers are not authorized to add craft items
            ret="You are not authorized to perform this action";
        }
        return ret;
    }

    public void deleteItem(long id){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CraftItem craftItem=craftItemRepository.findByCraftId(id);

        CraftCreatorCraftItem craftCreatorCraftItem=craftCreatorItemRepository.getByCraftItem(craftItem);

        //if Admin, notify craft creator
        if(userSession.getRole().getRoleId()==1){
            Notifications notifications=new Notifications();
            notifications.setNotification("The Craft Item "+craftItem.getCiName()+" was deleted by admin");
            notifications.setUsers(craftCreatorCraftItem.getCraftCreator().getUser());
            SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
            notifications.setDatetime(formatter.format(new Date()));
            notificationsRepository.save(notifications);
        }

        craftCreatorItemRepository.delete(craftCreatorCraftItem);
        craftItemRepository.delete(craftItem);
    }

    //only for admins and craft creators
    public void updateItem(ItemDTO itemDTO){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //try model mapper and test it first or do the other way
        CraftItem craftItem=craftItemRepository.findByCraftId(itemDTO.getCraftId());
        CraftCreatorCraftItem craftCreatorCraftItem=craftCreatorItemRepository.getByCraftItem(craftItem);

        List<String> editedParams=new ArrayList<>();

        if(!itemDTO.getCategory().equals(craftItem.getCategory())){
            craftItem.setCategory(itemDTO.getCategory());
            editedParams.add("Craft Category to "+itemDTO.getCategory());
        }

        if(itemDTO.getCiName()!=null){
            craftItem.setCiName(itemDTO.getCiName());
            editedParams.add("Craft Name to "+itemDTO.getCiName());
        }

        if(itemDTO.getCiPrice()!=0){
            craftItem.setCiPrice(itemDTO.getCiPrice());
            editedParams.add("Craft Price to "+itemDTO.getCiPrice());
        }

        if(itemDTO.getItemQuantity()!=0){
            craftItem.setItemQuantity(itemDTO.getItemQuantity());
            editedParams.add("Craft ItemQuantity to "+itemDTO.getItemQuantity());
        }

        if(itemDTO.getLongDescription()!=null){
            craftItem.setLongDescription(itemDTO.getLongDescription());
            editedParams.add("Long Description to"+itemDTO.getLongDescription());
        }

        if(itemDTO.getShortDescription()!=null){
            craftItem.setShortDescription(itemDTO.getShortDescription());
            editedParams.add("Short Description to "+itemDTO.getShortDescription());
        }

        if(itemDTO.getImgFile()!=null){
            craftItem.setImgFile(itemDTO.getImgFile());
            editedParams.add("Craft Image");
        }
        //if availabilityStatus is false, then set the quantity to 0
        if(itemDTO.isAvailabilityStatus()==false){
            craftItem.setItemQuantity(0);
            craftItem.setAvailabilityStatus(false);

            editedParams.add("Craft status to unavailable");
        }

        //if Admin, notify craft creator
        if(userSession.getRole().getRoleId()==1){
            Notifications notifications=new Notifications();
            String editedListString=String.join(",",editedParams);
            notifications.setNotification("The Craft Item "+editedListString+" was edited by admin");
            notifications.setUsers(craftCreatorCraftItem.getCraftCreator().getUser());

            SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
            notifications.setDatetime(formatter.format(new Date()));

            notificationsRepository.save(notifications);
        }

        craftItemRepository.save(craftItem);
    }

    public List<ItemDTO> getAllItems(){
        List<CraftItem> craftItems=craftItemRepository.findAll(); //can do orderby if it needs any sorting
        List<ItemDTO> dtoList=Utils.mapAll(craftItems,ItemDTO.class);

        List<CraftCreatorCraftItem> creatorCraftItems=craftCreatorItemRepository.findAll();

        for (CraftCreatorCraftItem cci:creatorCraftItems) {
            for (ItemDTO dtoitem:dtoList) {
                if(dtoitem.getCraftId()==cci.getCraftItem().getCraftId()){
                    dtoitem.setCreator(cci.getCraftCreator());
                }
            }
        }

        return dtoList;
    }

    public List<ItemDTO> getRecentlyAddedItems(){
        List<CraftItem> craftItems=craftItemRepository.getMostResentCrafts(PageRequest.of(0,12));
        List<ItemDTO> dtoList=Utils.mapAll(craftItems,ItemDTO.class);

        for (ItemDTO ci:dtoList) {
            CraftCreatorCraftItem craftCreatorCraftItem=craftCreatorItemRepository.findByCraftItem_CraftId(ci.getCraftId());
            ci.setCreator(craftCreatorCraftItem.getCraftCreator());
        }

        return dtoList;
    }

    public List<ItemDTO> getAllItemsOfCraftCreator(long id,int page){
        List<ItemDTO> dtoList=new ArrayList<>();
        Page<CraftCreatorCraftItem> creatorCraftItems;

        int pages=0;
        //for craft creators dashboard
        if(id == 0){
            UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CraftCreator craftCreator=craftCreatorRepository.findByUser_Id(userSession.getId());

            creatorCraftItems=craftCreatorItemRepository.findAllByCraftCreator_CreatorId(craftCreator.getCreatorId(),PageRequest.of(page,8));
            pages=creatorCraftItems.getTotalPages();
        } //for other users
        else{
            creatorCraftItems=craftCreatorItemRepository.findAllByCraftCreator_CreatorId(id,PageRequest.of(page,8));
            pages=creatorCraftItems.getTotalPages();
        }

        for (CraftCreatorCraftItem cci:creatorCraftItems) {
            ModelMapper mapper=new ModelMapper();
            ItemDTO itemDTO=mapper.map(cci.getCraftItem(),ItemDTO.class);
            itemDTO.setCreator(cci.getCraftCreator());
            itemDTO.setNoOfPages(pages);
            dtoList.add(itemDTO);
        }

        return dtoList;
    }

    public List<CreatorCraftOrderDTO> getOrdersForCreator(){
        List<CreatorCraftOrderDTO> craftOrderDTOList=new ArrayList<>();
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CraftCreator craftCreator=craftCreatorRepository.findByUser_Id(userSession.getId());

        List<CraftCreatorCraftItem> creatorCraftItems=craftCreatorItemRepository.getAllByCraftCreator(craftCreator);

        for (CraftCreatorCraftItem ccci:creatorCraftItems) {
            OrderCraftItem orderCraftItem=orderCraftItemRepository.getByOrder_OrderStatusAndCraftItemAndStatus("PURCHASED",ccci.getCraftItem(),"DELIVERY PENDING");

            if(orderCraftItem!=null){
                Cart cart=orderCraftItem.getOrder().getCart();

                CreatorCraftOrderDTO dto=new CreatorCraftOrderDTO();
                dto.setOrderCraftItemId(orderCraftItem.getId());
                dto.setCraftName(ccci.getCraftItem().getCiName());
                dto.setQuantity(orderCraftItem.getQuantity());
                dto.setOrderTotal(orderCraftItem.getOrder().getOrderTotal());
                dto.setUsername(cart.getUser().getUsername());
                dto.setPurchaseDate(orderCraftItem.getOrder().getPurchasedDate());

                craftOrderDTOList.add((dto));
            }

        }

        return craftOrderDTOList;
    }

    public void changeItemDeliveryStatus(long id){
        OrderCraftItem orderCraftItem=orderCraftItemRepository.getById(id);
        orderCraftItem.setStatus("DELIVERED");
        orderCraftItemRepository.save(orderCraftItem);
    }

    public List<ItemDTO> searchCrafts(String name){
        List<CraftItem> craftItemList=craftItemRepository.searchCrafts(name);
        List<ItemDTO> dtoList=Utils.mapAll(craftItemList,ItemDTO.class);

        for (ItemDTO ci:dtoList) {
            CraftCreatorCraftItem craftCreatorCraftItem=craftCreatorItemRepository.findByCraftItem_CraftId(ci.getCraftId());
            ci.setCreator(craftCreatorCraftItem.getCraftCreator());
        }
        return dtoList;
    }

    public List<ItemDTO> filterByType(String type){

        List<CraftItem> craftItemList=craftItemRepository.findAllByType(type);
        List<ItemDTO> dtoList=Utils.mapAll(craftItemList,ItemDTO.class);

        for (ItemDTO ci:dtoList) {
            CraftCreatorCraftItem craftCreatorCraftItem=craftCreatorItemRepository.findByCraftItem_CraftId(ci.getCraftId());
            ci.setCreator(craftCreatorCraftItem.getCraftCreator());
        }
        return dtoList;
    }

    public List<ItemDTO> filterByCatregory(String category){

        List<ItemDTO> dtoList=new ArrayList<>();
        List<CraftCreatorCraftItem> creatorCraftItems=craftCreatorItemRepository.findAllByCraftItem_Category(category);

        for (CraftCreatorCraftItem cci:creatorCraftItems) {
            ModelMapper mapper=new ModelMapper();
            ItemDTO itemDTO=mapper.map(cci.getCraftItem(),ItemDTO.class);
            itemDTO.setCreator(cci.getCraftCreator());
            dtoList.add(itemDTO);
        }

        return dtoList;
    }

    public ItemDTO getItem(long id){
        CraftItem craftItem= craftItemRepository.findByCraftId(id);
        ModelMapper mapper=new ModelMapper();

        return mapper.map(craftItem,ItemDTO.class);
    }


}
