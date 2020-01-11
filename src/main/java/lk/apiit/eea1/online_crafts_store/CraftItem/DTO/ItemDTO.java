package lk.apiit.eea1.online_crafts_store.CraftItem.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private long craftId;

    private String ciName;

    private boolean availabilityStatus;

    private double ciPrice;

    private double overallRating;

    private int itemQuantity;

    private String shortDescription;

    private String longDescription;
}
