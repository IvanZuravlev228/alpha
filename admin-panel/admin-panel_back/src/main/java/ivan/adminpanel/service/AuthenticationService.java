package ivan.adminpanel.service;

import ivan.adminpanel.model.User;

public interface AuthenticationService {
    User register(User user);

    User login(String login, String password) throws RuntimeException;
}