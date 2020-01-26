package lk.apiit.eea1.online_crafts_store.Cart.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
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

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private AllUsers user;
}
