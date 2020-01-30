package lk.apiit.eea1.online_crafts_store.CraftItem.DTO;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ItemDTO {
    private long craftId;

    private String ciName;

    private boolean availabilityStatus;

    private double ciPrice;

//    @JsonDeserialize(as = MultipartFile.class)
//    private MultipartFile imgFile;
    private String img;

    private int itemQuantity;

    private String shortDescription;

    private String longDescription;

    private String category;

    private String type; //ready made or craft kit

    private CraftCreator creator;

    private int noOfPages;
}
