package lk.apiit.eea1.online_crafts_store.Cart.Repository;

import lk.apiit.eea1.online_crafts_store.Cart.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart getByUser_Id(long id);
}
