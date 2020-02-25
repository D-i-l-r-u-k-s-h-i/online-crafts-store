package lk.apiit.eea1.online_crafts_store.Notifications.Service;

import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import lk.apiit.eea1.online_crafts_store.Notifications.DTO.NotificationsDTO;
import lk.apiit.eea1.online_crafts_store.Notifications.Entity.Notifications;
import lk.apiit.eea1.online_crafts_store.Notifications.Repository.NotificationsRepository;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsService {

    @Autowired
    NotificationsRepository notificationsRepository;

    public List<NotificationsDTO> getNotificationsOfUser(){
        UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Notifications> notificationsList=notificationsRepository.getAllByUsers_Id(userSession.getId());

        return Utils.mapAll(notificationsList,NotificationsDTO.class);
    }

    public void deleteNotification(long id){
        Notifications notification=notificationsRepository.getByNotificationId(id);
        notificationsRepository.delete(notification);
    }
}
