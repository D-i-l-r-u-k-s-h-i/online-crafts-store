package lk.apiit.eea1.online_crafts_store.Order.Controller;

import lk.apiit.eea1.online_crafts_store.Order.DTO.OrderDTO;
import lk.apiit.eea1.online_crafts_store.Order.Service.OrderService;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/buyItem",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buyItem(@RequestHeader(value = "Authorization") String token, @RequestBody OrderDTO orderDTO) throws Exception {
        Utils.checkToken(token);
        orderService.buyItem(orderDTO);
        return ResponseEntity.ok("Purchase successful");
    }

    @RequestMapping(value = "/buy",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> buyOrder(@RequestHeader(value = "Authorization") String token) throws Exception {
        Utils.checkToken(token);
        orderService.buyOrder();
        return ResponseEntity.ok("Purchase successful");
    }

    @RequestMapping(value = "/addtocart/{itemid}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addItemToCart(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "itemid") long itemid) throws Exception {
        Utils.checkToken(token);
        orderService.addItemToCart(itemid);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @RequestMapping(value = "/removefromcart/{itemid}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeItemFromCart(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "itemid") long itemid) throws Exception {
        Utils.checkToken(token);
        orderService.removeItemFromCart(itemid);
        return ResponseEntity.ok("Item removed from cart successfully");
    }

    @RequestMapping(value = "/itemquantity",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeItemQuantity(@RequestHeader(value = "Authorization") String token, @RequestBody OrderDTO orderDTO) throws Exception {
        Utils.checkToken(token);
        orderService.changeQuantityOfItem(orderDTO);
        return ResponseEntity.ok("Quantity changed successfully");
    }

    @RequestMapping(value = "/cart",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCart(@RequestHeader(value = "Authorization") String token) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(orderService.viewCart());
    }

    @RequestMapping(value = "/ordertotal",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> calculateTotal(@RequestHeader(value = "Authorization") String token) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(orderService.calculateOrderTotForDisplay());
    }

}
