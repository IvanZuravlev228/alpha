package ivan.adminpanel.service;

import ivan.adminpanel.model.Session;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SessionService {
    List<Session> getAll();

    void deleteAll();

    String getSessionUrlById(String id);

    String getAuthUrlById(String id);

    List<Session> getByOwner(String owner, Pageable pageable);

    void update(String id);

    void deleteById(String id);
}
