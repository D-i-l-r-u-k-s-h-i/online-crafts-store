package lk.apiit.eea1.online_crafts_store.Order.DTO;

import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    private long craftId;

    private int quantity;

    private CraftItem craftItem;
}
