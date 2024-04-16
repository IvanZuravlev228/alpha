package ivan.adminpanel.dto.user;

import lombok.Data;

@Data
public class UserLoginDto {
    private String login;
    private String password;
}