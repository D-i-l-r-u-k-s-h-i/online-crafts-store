package lk.apiit.eea1.online_crafts_store.Auth.Controller;

import lk.apiit.eea1.online_crafts_store.Auth.DTO.UserDTO;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.LoginRequest;
import lk.apiit.eea1.online_crafts_store.Auth.JwtAuthenticationResponse;
import lk.apiit.eea1.online_crafts_store.Auth.JwtTokenProvider;
import lk.apiit.eea1.online_crafts_store.Auth.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@ResponseBody
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailService customUserDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerNewCustomer(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(customUserDetailsService.saveCustomerOrCreator(userDTO));
    }

    @RequestMapping(value = "/updatepwd",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePwd(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(customUserDetailsService.updatePwd(userDTO));
    }
}
