package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.Entity;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customer_rate_craft_creator" ,schema = "public")
public class RateCraftCreator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName="id",nullable = false)
    private AllUsers user;

    @ManyToOne
    @JoinColumn(name = "creator_id",nullable = false)
    private CraftCreator craftCreator;

    private int rating;
}
