package lk.apiit.eea1.online_crafts_store.Order.Entity;

import lk.apiit.eea1.online_crafts_store.Cart.Entity.Cart;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order" ,schema = "public")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private String orderStatus;

    private double orderTotal;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;
}
