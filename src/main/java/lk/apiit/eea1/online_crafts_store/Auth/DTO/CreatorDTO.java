package lk.apiit.eea1.online_crafts_store.Auth.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreatorDTO {
    private long creatorId;

    private String creatorName;

    private String creatorEmail;

    private double overallRating;
}
