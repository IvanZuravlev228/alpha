package ivan.adminpanel.dto.user;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String login;
    private String password;
    private String owner;
}
