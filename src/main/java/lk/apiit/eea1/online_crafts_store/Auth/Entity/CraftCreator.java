package lk.apiit.eea1.online_crafts_store.Auth.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "craft_creator" ,schema = "public")
public class CraftCreator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long creatorId;

    private String creatorName;

    private String creatorEmail;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = false)
    private AllUsers user;

    private double overallRating;
}
