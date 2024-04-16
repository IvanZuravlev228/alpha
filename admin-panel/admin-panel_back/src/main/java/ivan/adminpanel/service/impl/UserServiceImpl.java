package ivan.adminpanel.service.impl;

import ivan.adminpanel.model.User;
import ivan.adminpanel.repository.UserRepository;
import ivan.adminpanel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
