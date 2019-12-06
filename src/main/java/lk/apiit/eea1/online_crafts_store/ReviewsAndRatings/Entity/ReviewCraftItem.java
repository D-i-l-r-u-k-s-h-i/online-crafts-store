package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.Customer;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customer_review_craft_item" ,schema = "public")
public class ReviewCraftItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cust_id",nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "craft_id",nullable = false)
    private CraftItem craftItem;

    private String reviewDescription;
}
