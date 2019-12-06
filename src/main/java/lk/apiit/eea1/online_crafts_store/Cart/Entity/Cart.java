package lk.apiit.eea1.online_crafts_store.Cart.Entity;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.Customer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart" ,schema = "public")
public class Cart {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartId;

    @ManyToOne
    @JoinColumn(name = "cust_id",nullable = false)
    private Customer customer;
}
