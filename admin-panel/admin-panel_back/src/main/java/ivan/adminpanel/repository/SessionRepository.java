package ivan.adminpanel.repository;

import ivan.adminpanel.model.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String> {
    List<Session> findByOwner(String owner, Pageable pageable);
}
