package lk.apiit.eea1.online_crafts_store.Order.Repository;

import lk.apiit.eea1.online_crafts_store.Cart.Entity.Cart;
import lk.apiit.eea1.online_crafts_store.Order.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order getByCartAndOrderStatus(Cart cart,String status);

    List<Order> getAllByCartAndOrderStatus(Cart cart,String status);

}
