package lk.apiit.eea1.online_crafts_store.CraftItem.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreatorCraftOrderDTO {
    private long orderCraftItemId;

    private String username;

    private String craftName;

    private int quantity;

    private double orderTotal;

    private String purchaseDate;

}
