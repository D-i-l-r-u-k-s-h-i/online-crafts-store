package lk.apiit.eea1.online_crafts_store.CraftItem.Controller;

import lk.apiit.eea1.online_crafts_store.CraftItem.DTO.ItemDTO;
import lk.apiit.eea1.online_crafts_store.CraftItem.Service.CraftItemService;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/craft")
@Controller
public class CraftItemController {
    @Autowired
    CraftItemService craftItemService;

    @RequestMapping(value = "/getall",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCraftItems(@RequestHeader(value = "Authorization") String token) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.getAllItems());
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCraftItem(@RequestHeader(value = "Authorization") String token, @RequestBody ItemDTO dto, @PathVariable MultipartFile file) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.addItem(dto));
    }

    @RequestMapping(value = "/mostrecent",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecentlyAddedCraftItems(@RequestHeader(value = "Authorization") String token) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.getRecentlyAddedItems());
    }

    @RequestMapping(value = "/creator/{id}/{page}",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCraftItemsOfCreator(@RequestHeader(value = "Authorization") String token,@PathVariable(name = "id") long id,@PathVariable(name = "page") int page) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.getAllItemsOfCraftCreator(id,page));
    }

    @RequestMapping(value = "/search/{value}",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchCraftItems(@RequestHeader(value = "Authorization") String token,@PathVariable(name = "value") String value) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.searchCrafts(value));
    }

    @RequestMapping(value = "/filtercategory/{category}",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterByCategory(@RequestHeader(value = "Authorization") String token,@PathVariable(name = "category") String category) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.filterByCatregory(category));
    }

    @RequestMapping(value = "/filtertype/{type}",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> filterByType(@RequestHeader(value = "Authorization") String token,@PathVariable(name = "type") String type) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.filterByType(type));
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteItem(@RequestHeader(value = "Authorization") String token,@PathVariable(name = "id") long id) throws Exception {
        Utils.checkToken(token);
        craftItemService.deleteItem(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCraftItem(@RequestHeader(value = "Authorization") String token, @RequestBody ItemDTO dto) throws Exception {
        Utils.checkToken(token);
        craftItemService.updateItem(dto);
        return ResponseEntity.ok("Successfuly updated.");
    }

    @RequestMapping(value = "/craftorders",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCraftOrdersForCreator(@RequestHeader(value = "Authorization") String token) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(craftItemService.getOrdersForCreator());
    }

    @RequestMapping(value = "/deliver/{id}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeDeliveryStatus(@RequestHeader(value = "Authorization") String token,@PathVariable(name = "id") long id) throws Exception {
        Utils.checkToken(token);
        craftItemService.changeItemDeliveryStatus(id);
        return ResponseEntity.ok("DELEVERED");
    }
}
