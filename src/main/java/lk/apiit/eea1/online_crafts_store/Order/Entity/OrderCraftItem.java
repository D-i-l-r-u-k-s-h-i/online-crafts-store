package lk.apiit.eea1.online_crafts_store.Order.Entity;

import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_craft_item" ,schema = "public")
public class OrderCraftItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "craft_id",nullable = false)
    private CraftItem craftItem;

    private int quantity;

    private String status;
}
