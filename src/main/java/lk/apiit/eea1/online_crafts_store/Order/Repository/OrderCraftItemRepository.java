package lk.apiit.eea1.online_crafts_store.Order.Repository;

import lk.apiit.eea1.online_crafts_store.Order.Entity.OrderCraftItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCraftItemRepository extends JpaRepository<OrderCraftItem,Long> {
}
