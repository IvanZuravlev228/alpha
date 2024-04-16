package ivan.adminpanel.dto.mapper;

import ivan.adminpanel.dto.user.UserRegisterDto;
import ivan.adminpanel.dto.user.UserResponseDto;
import ivan.adminpanel.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toModel(UserRegisterDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setOwner(dto.getOwner());
        return user;
    }

    public UserResponseDto toDto(User model) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(model.getId());
        dto.setLogin(model.getLogin());
        dto.setRole(model.getRole());
        dto.setOwner(model.getOwner());
        return dto;
    }
}
