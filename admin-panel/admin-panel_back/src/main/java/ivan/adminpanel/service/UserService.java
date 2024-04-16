package ivan.adminpanel.service;

import ivan.adminpanel.model.User;

public interface UserService {
    User getByLogin(String login);
}
