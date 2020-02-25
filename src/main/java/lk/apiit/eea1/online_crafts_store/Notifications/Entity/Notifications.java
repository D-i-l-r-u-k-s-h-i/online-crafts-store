package lk.apiit.eea1.online_crafts_store.Notifications.Entity;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "notifications" ,schema = "public")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    private String notification;

    private String datetime;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id", nullable = false)
    private AllUsers users;
}
