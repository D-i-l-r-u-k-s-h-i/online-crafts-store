package lk.apiit.eea1.online_crafts_store.Auth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lk.apiit.eea1.online_crafts_store.Cart.Entity.Cart;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "all_users" ,schema = "public")
public class AllUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    @JsonIgnore
    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "user")
    private Customer customer;

    @JsonIgnore
    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "user")
    private Admin admin;

    @JsonIgnore
    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "user")
    private CraftCreator craftCreator;

    @JsonIgnore
    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "user")
    private Cart cart;
}
