package ivan.adminpanel.service.impl;

import java.util.NoSuchElementException;

import ivan.adminpanel.model.User;
import ivan.adminpanel.repository.UserRepository;
import ivan.adminpanel.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(String login, String password) throws RuntimeException {
        User user = userRepository.findByLogin(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect username or password");
        }
        return user;
    }
}