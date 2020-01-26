package lk.apiit.eea1.online_crafts_store.CraftItem.Service;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.RoleName;
import lk.apiit.eea1.online_crafts_store.Auth.Repository.CraftCreatorRepository;
import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import lk.apiit.eea1.online_crafts_store.CraftItem.DTO.ItemDTO;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftCreatorCraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftCreatorItemRepository;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftItemRepository;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CraftItemService {

    @Autowired
    CraftItemRepository craftItemRepository;

    @Autowired
    CraftCreatorItemRepository craftCreatorItemRepository;

    @Autowired
    CraftCreatorRepository craftCreatorRepository;

    //only for admins and craft creators
    @Transactional    //because craft item and images should be saved at the same time, rollback if there is any problem
    public String addItem(ItemDTO dto) throws IOException {
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

//            craftItem.setImg(dto.getImgFile().getBytes());
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
        CraftItem craftItem=craftItemRepository.findByCraftId(id);
        craftItemRepository.delete(craftItem);
    }

    //only for admins and craft creators
    public void updateItem(){
        //try model mapper and test it first or do the other way
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

    public List<ItemDTO> getAllItemsOfCraftCreator(long id){ //id or userName?
        List<ItemDTO> dtoList=new ArrayList<>();
        List<CraftCreatorCraftItem> creatorCraftItems;
        //for craft creators dashboard
        if(id == 0){
            UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CraftCreator craftCreator=craftCreatorRepository.findByUser_Id(userSession.getId());

            creatorCraftItems=craftCreatorItemRepository.findAllByCraftCreator_CreatorId(craftCreator.getCreatorId());
        } //for other users
        else{
            creatorCraftItems=craftCreatorItemRepository.findAllByCraftCreator_CreatorId(id);
        }

        for (CraftCreatorCraftItem cci:creatorCraftItems) {
            ModelMapper mapper=new ModelMapper();
            ItemDTO itemDTO=mapper.map(cci.getCraftItem(),ItemDTO.class);
            itemDTO.setCreator(cci.getCraftCreator());
            dtoList.add(itemDTO);
        }

        return dtoList;
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
