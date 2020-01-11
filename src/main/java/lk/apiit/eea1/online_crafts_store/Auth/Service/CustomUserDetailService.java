package lk.apiit.eea1.online_crafts_store.Auth.Service;

import lk.apiit.eea1.online_crafts_store.Auth.DTO.UserDTO;
import lk.apiit.eea1.online_crafts_store.Auth.Entity.*;
import lk.apiit.eea1.online_crafts_store.Auth.Repository.AdminRepository;
import lk.apiit.eea1.online_crafts_store.Auth.Repository.CraftCreatorRepository;
import lk.apiit.eea1.online_crafts_store.Auth.Repository.CustomerRepository;
import lk.apiit.eea1.online_crafts_store.Auth.Repository.UserRepository;
import lk.apiit.eea1.online_crafts_store.Auth.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CraftCreatorRepository craftCreatorRepository;

    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        AllUsers user = userRepository.findByUsername(username);
        return UserSession.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<AllUsers> user = userRepository.findById(id);
        return UserSession.create(user.get());
    }

    @Transactional
    public String saveCustomerOrCreator(UserDTO userDTO){
        String ret="";

        AllUsers user=userRepository.findByUsername(userDTO.getUserName());

        if(user!=null){
            ret = "Sorry this name is taken";
        }
        else{
            //check confirm password
            if(userDTO.getPassword().equals(userDTO.getConfirmPassword())){
                String custPwd= userDTO.getPassword();
                String pwd = new BCryptPasswordEncoder().encode(custPwd);

                AllUsers newuser=new AllUsers();
                newuser.setUsername(userDTO.getUserName());
                newuser.setPassword(pwd);

                if(userDTO.getUserType().equals("CUSTOMER")){
                    Customer cust_by_email=customerRepository.findByCustEmail(userDTO.getEmail());
                    if(cust_by_email!=null){
                        ret="Sorry, user with this Email already exists";
                    }

                    newuser.setRole(new Role(2, RoleName.ROLE_CUSTOMER));
                    Customer newCust=new Customer();

                    newCust.setCustName(userDTO.getUserName());
                    newCust.setCustEmail(userDTO.getEmail());

                    newCust.setUser(newuser);

                    userRepository.save(newuser);
                    customerRepository.save(newCust);
                }
                else if(userDTO.getUserType().equals("CREATOR")){
                    CraftCreator creator_by_email=craftCreatorRepository.findByCreatorEmail(userDTO.getEmail());
                    //This way there wont be any duplicate emails in the creator not the customer table
                    if(creator_by_email!=null){
                        ret="Sorry, user with this Email already exists";
                    }

                    newuser.setRole(new Role(3, RoleName.ROLE_CRAFT_CREATOR));
                    CraftCreator craftCreator=new CraftCreator();
                    craftCreator.setCreatorName(userDTO.getUserName());
                    craftCreator.setCreatorEmail(userDTO.getEmail());
                    craftCreator.setUser(newuser);

                    userRepository.save(newuser);
                    craftCreatorRepository.save(craftCreator);
                }

                ret="Successful registration";
            }
            else {
                ret="Passwords doesn't match";
            }

        }

        return ret;
    }


    //signup for already a customer or craft creator
    //a method here
    //if a creator, check customer table for email, username and pair it as one user.


    public String updatePwd(UserDTO userDTO){
        String response="";
        AllUsers u = userRepository.findByUsername(userDTO.getUserName());
        if (u != null) {
//            if (bCryptPasswordEncoder.matches(pwdUpdate.getOld_password(), u.getPassword())) {
            if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                String pwd = new BCryptPasswordEncoder().encode(userDTO.getPassword());
                u.setPassword(pwd);
                userRepository.save(u);
                response.concat("successful password update");
            } else {
                response.concat("Password missmatch");
            }

        } else {
            response.concat("invalid User");
        }
        return response;
    }

//    @Transactional
//    public String saveAdmin(AdminDTO adminDTO){
//        String ret="";
//
//        AllUsers user=userRepository.findByUsername(adminDTO.getUsername());
//
//        if(user!=null){
//            ret = "Sorry this name is taken";
//        }
//        else{
//            UserSession userSession = (UserSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//            if(bCryptPasswordEncoder.matches(adminDTO.getCurrentAdminPass(),userSession.getPassword()) && userSession.getRole().getRoleId()==1){
//                String pwd = bCryptPasswordEncoder.encode(adminDTO.getPassword());
//
//                ModelMapper modelMapper= new ModelMapper();
//                Admin admin=modelMapper.map(adminDTO,Admin.class);
//                AllUsers newuser=modelMapper.map(adminDTO,AllUsers.class);
//                newuser.setPassword(pwd);
//                newuser.setRole(new Role(1,RoleName.ROLE_ADMIN));
////                admin.setRole(new Role(1,RoleName.ROLE_ADMIN));
//
//                adminRepository.save(admin);
//                userRepository.save(newuser);
//
//                ret="Successful Registration";
//            }
//            else {
//                ret="Sorry, incorrect password";
//            }
//
//        }
//        return ret;
//    }
}
