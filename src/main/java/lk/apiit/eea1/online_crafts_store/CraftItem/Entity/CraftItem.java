package lk.apiit.eea1.online_crafts_store.CraftItem.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "craft_item" ,schema = "public")
public class CraftItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long craftId;

    private String ciName;

    private boolean availabilityStatus;

    private double ciPrice;

    private byte[] imgFile;

    //for now using URL
//    private String img;

    private int itemQuantity;

    private String shortDescription;

    private String longDescription;

    private String category;

    private String type; //ready made or craft kit

}
