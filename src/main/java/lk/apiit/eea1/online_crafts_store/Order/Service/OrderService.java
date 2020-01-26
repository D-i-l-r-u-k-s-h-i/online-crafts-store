package lk.apiit.eea1.online_crafts_store.Order.Service;

import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import lk.apiit.eea1.online_crafts_store.Cart.Entity.Cart;
import lk.apiit.eea1.online_crafts_store.Cart.Repository.CartRepository;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lk.apiit.eea1.online_crafts_store.CraftItem.Repository.CraftItemRepository;
import lk.apiit.eea1.online_crafts_store.Order.DTO.OrderDTO;
import lk.apiit.eea1.online_crafts_store.Order.Entity.Order;
import lk.apiit.eea1.online_crafts_store.Order.Entity.OrderCraftItem;
import lk.apiit.eea1.online_crafts_store.Order.Repository.OrderCraftItemRepository;
import lk.apiit.eea1.online_crafts_store.Order.Repository.OrderRepository;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderCraftItemRepository orderCraftItemRepository;

    @Autowired
    CraftItemRepository craftItemRepository;

    @Autowired
    CartRepository cartRepository;

    public void buyOrder(){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        Order order=orderRepository.getByCartAndOrderStatus(cart,"PENDING");
        order.setOrderTotal(calculateOrderTotalBeforeBuy(order));
        order.setOrderStatus("PURCHASED");

        orderRepository.save(order);

    }

    @Transactional
    public void buyItem(OrderDTO orderDTO){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        CraftItem craftItem=orderDTO.getCraftItem();

        Order order=new Order();
        order.setOrderStatus("PURCHASED");
        order.setCart(cart); //to identify user if needed later
        order.setOrderTotal(craftItem.getCiPrice()*orderDTO.getQuantity());

        orderRepository.save(order);

        OrderCraftItem orderCraftItem=new OrderCraftItem();
        orderCraftItem.setQuantity(orderDTO.getQuantity());
        orderCraftItem.setCraftItem(craftItem);
        orderCraftItem.setOrder(order);

        orderCraftItemRepository.save(orderCraftItem);

        int availableQty=craftItem.getItemQuantity()-orderDTO.getQuantity();
        craftItem.setItemQuantity(availableQty);
        if(availableQty==0){
            craftItem.setAvailabilityStatus(false);
        }

        craftItemRepository.save(craftItem);

    }

    @Transactional
    public void addItemToCart(long itemId){

        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        CraftItem craftItem=craftItemRepository.findByCraftId(itemId);

        //get pending orders
        Order order=orderRepository.getByCartAndOrderStatus(cart,"PENDING");
        if(order==null){
            order=new Order();
            order.setCart(cart);
            order.setOrderStatus("PENDING");
            orderRepository.save(order);
        }

        List<OrderCraftItem> orderCraftItemList=orderCraftItemRepository.getAllByCraftItemAndOrder(craftItem,order);
        //to check for duplicates


        if(orderCraftItemList.size()>=1){
            //if needed check whether the item quantity is 0
            orderCraftItemList.get(0).setQuantity(orderCraftItemList.get(0).getQuantity()+1);
            orderCraftItemRepository.save(orderCraftItemList.get(0));
        }else {
            OrderCraftItem orderCraftItem=new OrderCraftItem();
            orderCraftItem.setOrder(order);
            orderCraftItem.setCraftItem(craftItem);
            orderCraftItem.setQuantity(1);

            orderCraftItemRepository.save(orderCraftItem);
        }

    }

    @Transactional
    public void removeItemFromCart(long itemId){

        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        CraftItem craftItem=craftItemRepository.findByCraftId(itemId);

        //get pending orders
        Order order=orderRepository.getByCartAndOrderStatus(cart,"PENDING");

        OrderCraftItem orderCraftItem=orderCraftItemRepository.getByCraftItemAndOrder(craftItem,order);

        orderCraftItemRepository.delete(orderCraftItem);
    }

    public List<OrderDTO> viewCart(){

        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        Order order=orderRepository.getByCartAndOrderStatus(cart,"PENDING");

        List<OrderCraftItem> orderCraftItemList=orderCraftItemRepository.getAllByOrder(order);

        return Utils.mapAll(orderCraftItemList,OrderDTO.class);
    }

    @Transactional
    public void changeQuantityOfItem(OrderDTO dto){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        CraftItem craftItem=craftItemRepository.findByCraftId(dto.getCraftId());

        Order order=orderRepository.getByCartAndOrderStatus(cart,"PENDING");
        //get the item
        OrderCraftItem orderCraftItem=orderCraftItemRepository.getByCraftItemAndOrder(craftItem,order);
        orderCraftItem.setQuantity(dto.getQuantity());

        orderCraftItemRepository.save(orderCraftItem);

    }

    @Transactional
    public double calculateOrderTotalBeforeBuy(Order order){
        double tot=0;
        List<OrderCraftItem> orderCraftItemList=orderCraftItemRepository.getAllByOrder(order);

        for (OrderCraftItem oci:orderCraftItemList) {
            //reduce the quantity from CraftItem
            CraftItem craftItem=oci.getCraftItem();

            if(craftItem.isAvailabilityStatus() && oci.getQuantity()<=craftItem.getItemQuantity()){
                tot+=craftItem.getCiPrice()*oci.getQuantity();
            }
            else if(craftItem.isAvailabilityStatus() && oci.getQuantity()>craftItem.getItemQuantity()){
                tot+=craftItem.getCiPrice()*craftItem.getItemQuantity();
                oci.setQuantity(craftItem.getItemQuantity());
                orderCraftItemRepository.save(oci);
            }

            int availableQty=craftItem.getItemQuantity()-oci.getQuantity();
            craftItem.setItemQuantity(availableQty);
            if(availableQty==0){
                craftItem.setAvailabilityStatus(false);
            }
            craftItemRepository.save(craftItem);
        }

        return tot;
    }

    public double calculateOrderTotForDisplay(){
        double tot=0;
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Cart cart =cartRepository.getByUser_Id(userSession.getId());

        Order order=orderRepository.getByCartAndOrderStatus(cart,"PENDING");

        List<OrderCraftItem> orderCraftItemList=orderCraftItemRepository.getAllByOrder(order);

        for (OrderCraftItem oci:orderCraftItemList) {
            if(oci.getCraftItem().isAvailabilityStatus() && oci.getQuantity()<=oci.getCraftItem().getItemQuantity()){
                tot+=oci.getCraftItem().getCiPrice()*oci.getQuantity();
            }
            else if(oci.getCraftItem().isAvailabilityStatus() && oci.getQuantity()>oci.getCraftItem().getItemQuantity()){
                tot+=oci.getCraftItem().getCiPrice()*oci.getCraftItem().getItemQuantity();
                oci.setQuantity(oci.getCraftItem().getItemQuantity());
                orderCraftItemRepository.save(oci);
            }
        }

        return tot;
    }
}
