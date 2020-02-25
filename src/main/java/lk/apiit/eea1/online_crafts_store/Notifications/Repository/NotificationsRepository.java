package lk.apiit.eea1.online_crafts_store.Notifications.Repository;

import lk.apiit.eea1.online_crafts_store.Auth.Entity.AllUsers;
import lk.apiit.eea1.online_crafts_store.Notifications.Entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications,Long> {

    List<Notifications> getAllByUsers_Id(long id);

    Notifications getByNotificationId(long id);
}
