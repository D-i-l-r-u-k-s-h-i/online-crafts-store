package lk.apiit.eea1.online_crafts_store.CraftItem.Service;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.RoleName;
import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import lk.apiit.eea1.online_crafts_store.CraftItem.DTO.ItemDTO;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftItemRepository;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CraftItemService {

    @Autowired
    CraftItemRepository craftItemRepository;

    //only for admins and craft creators
    public String addItem(ItemDTO dto){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //do the role based access to this api if only needed
        String ret="";
        if(userSession.getRole().getRoleName().equals(RoleName.ROLE_ADMIN) || userSession.getRole().getRoleName().equals(RoleName.ROLE_CRAFT_CREATOR)){
            ModelMapper modelMapper = new ModelMapper();
            CraftItem craftItem=modelMapper.map(dto,CraftItem.class);

            craftItemRepository.save(craftItem);
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
        //tty model mapper and test it first or do the other way
    }

    public List<ItemDTO> getAllItems(){
        List<CraftItem> craftItems=craftItemRepository.findAll(); //can do orderby if it needs any sorting
        return Utils.mapAll(craftItems,ItemDTO.class);
    }

    public void getAllItemsOfCraftCreator(long id){ //id or userName?


    }

    public ItemDTO getItem(long id){
        CraftItem craftItem= craftItemRepository.findByCraftId(id);
        ModelMapper mapper=new ModelMapper();

        return mapper.map(craftItem,ItemDTO.class);
    }
}
