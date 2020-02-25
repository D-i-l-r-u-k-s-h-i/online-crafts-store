package lk.apiit.eea1.online_crafts_store.CraftItem.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.CraftCreator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import java.io.Serializable;

@Getter
@Setter
public class ItemDTO implements Serializable {
    private long craftId;

    private String ciName;

    private boolean availabilityStatus;

    private double ciPrice;

    private byte[] imgFile;

//    private String img;

    private int itemQuantity;

    private String shortDescription;

    private String longDescription;

    private String category;

    private String type; //ready made or craft kit

    private CraftCreator creator;

    private int noOfPages;
}
