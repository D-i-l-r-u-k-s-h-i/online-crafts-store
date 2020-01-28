package lk.apiit.eea1.online_crafts_store.Order.DTO;

import lk.apiit.eea1.online_crafts_store.Order.Entity.OrderCraftItem;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserOrdersDTO {

    private List<OrderCraftItem> orderItemsList;

    private double orderTotal;

    private String purchaseDate;

    private String orderStatus;

}
