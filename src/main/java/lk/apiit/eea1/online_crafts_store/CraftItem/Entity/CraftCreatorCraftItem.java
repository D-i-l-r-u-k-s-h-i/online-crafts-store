package lk.apiit.eea1.online_crafts_store.CraftItem.Entity;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "craft_creator_craft_item" ,schema = "public")
public class CraftCreatorCraftItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "creator_id",nullable = false)
    private CraftCreator craftCreator;

    @ManyToOne
    @JoinColumn(name = "craft_id",nullable = false)
    private CraftItem craftItem;
}
