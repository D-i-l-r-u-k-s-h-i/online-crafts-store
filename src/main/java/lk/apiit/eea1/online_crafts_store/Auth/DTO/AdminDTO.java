package lk.apiit.eea1.online_crafts_store.Auth.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private long roleId;

    private String currentAdminPass;
}
