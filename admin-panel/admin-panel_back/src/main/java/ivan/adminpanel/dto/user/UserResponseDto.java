package ivan.adminpanel.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String login;
    private String owner;
    private String role;
}
