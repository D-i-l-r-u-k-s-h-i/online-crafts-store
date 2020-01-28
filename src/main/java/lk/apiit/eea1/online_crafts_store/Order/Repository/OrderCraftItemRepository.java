package lk.apiit.eea1.online_crafts_store.Order.Repository;

import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lk.apiit.eea1.online_crafts_store.Order.Entity.Order;
import lk.apiit.eea1.online_crafts_store.Order.Entity.OrderCraftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCraftItemRepository extends JpaRepository<OrderCraftItem,Long> {
    List<OrderCraftItem> getAllByOrder(Order order);

    OrderCraftItem getByCraftItemAndOrder(CraftItem craftItem,Order order);

    List<OrderCraftItem> getAllByCraftItemAndOrder(CraftItem craftItem,Order order);

    OrderCraftItem getByOrder_OrderStatusAndCraftItemAndStatus(String orderstatus,CraftItem craftItem,String itemstatus);

    OrderCraftItem getById(long id);
}
