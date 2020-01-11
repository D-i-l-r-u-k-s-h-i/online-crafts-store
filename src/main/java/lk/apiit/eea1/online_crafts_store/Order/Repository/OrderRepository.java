package lk.apiit.eea1.online_crafts_store.Order.Repository;

import lk.apiit.eea1.online_crafts_store.Order.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
