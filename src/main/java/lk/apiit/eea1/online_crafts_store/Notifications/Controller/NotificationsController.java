package lk.apiit.eea1.online_crafts_store.Notifications.Controller;

import lk.apiit.eea1.online_crafts_store.Notifications.Service.NotificationsService;
import lk.apiit.eea1.online_crafts_store.Util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    NotificationsService notificationsService;

    @RequestMapping(value = "/getallofuser",method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationsOfUser(@RequestHeader(value = "Authorization") String token) throws Exception {
        Utils.checkToken(token);
        return ResponseEntity.ok(notificationsService.getNotificationsOfUser());
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNotification(@RequestHeader(value = "Authorization") String token,@PathVariable(name = "id") long id) throws Exception {
        Utils.checkToken(token);
        notificationsService.deleteNotification(id);
        return ResponseEntity.ok("Successfully deleted");
    }
}
