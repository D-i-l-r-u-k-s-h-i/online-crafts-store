package lk.apiit.eea1.online_crafts_store.Auth.Controller;

import lk.apiit.eea1.online_crafts_store.Auth.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@ResponseBody
public class AdminController {
    @Autowired
    CustomUserDetailService customUserDetailService;

//    @RequestMapping(value = "/register",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> addAdmin(@RequestBody AdminDTO adminDTO, @RequestHeader(value = "Authorization") String token) throws Exception {
//        Utils.checkToken(token);
//        return ResponseEntity.ok(customUserDetailService.saveAdmin(adminDTO));
//    }
}
