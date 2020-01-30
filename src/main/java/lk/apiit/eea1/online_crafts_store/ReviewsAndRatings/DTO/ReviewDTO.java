package lk.apiit.eea1.online_crafts_store.ReviewsAndRatings.DTO;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
import lk.apiit.eea1.online_crafts_store.CraftItem.Entity.CraftItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

    private long craftId;

    private String reviewDescription;

    private CraftItem craftItem; //for display

    private AllUsers user;

    private String date;
}
