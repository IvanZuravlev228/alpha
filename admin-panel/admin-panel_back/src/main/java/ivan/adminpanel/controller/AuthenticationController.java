package ivan.adminpanel.controller;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ivan.adminpanel.dto.mapper.UserMapper;
import ivan.adminpanel.dto.user.UserLoginDto;
import ivan.adminpanel.dto.user.UserRegisterDto;
import ivan.adminpanel.dto.user.UserResponseDto;
import ivan.adminpanel.model.User;
import ivan.adminpanel.security.jwt.JwtTokenProvider;
import ivan.adminpanel.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://185.65.105.40")
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRegisterDto user) {
        return new ResponseEntity<>(userMapper.toDto(
                authenticationService.register(userMapper.toModel(user))), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginDto userLoginDto)
            throws RuntimeException {
        User user = authenticationService.login(
                userLoginDto.getLogin(), userLoginDto.getPassword());
        Set<String> roles = new HashSet<>();
        roles.add(user.getRole());
        String token = jwtTokenProvider.createToken(user.getLogin(), roles);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return new ResponseEntity<>(tokenMap, HttpStatus.OK);
    }
}
