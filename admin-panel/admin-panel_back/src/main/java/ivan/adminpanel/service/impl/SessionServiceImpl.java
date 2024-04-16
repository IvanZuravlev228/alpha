package ivan.adminpanel.service.impl;

import ivan.adminpanel.model.Session;
import ivan.adminpanel.repository.SessionRepository;
import ivan.adminpanel.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository repository;

    @Override
    public List<Session> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public String getSessionUrlById(String id) {
        Optional<Session> session = repository.findById(id);
        return session.map(Session::getFileURL).orElse(null);
    }

    @Override
    public String getAuthUrlById(String id) {
        Optional<Session> session = repository.findById(id);
        return session.map(Session::getAuthURL).orElse(null);
    }

    @Override
    public List<Session> getByOwner(String owner, Pageable pageable) {
        return repository.findByOwner(owner, pageable);
    }

    @Override
    public void update(String id) {
        Optional<Session> existingSession = repository.findById(id);

        existingSession.ifPresent(existing -> {
            existing.setIsDownload("yes");
            repository.save(existing);
        });
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
