package ivan.adminpanel.controller;

import ivan.adminpanel.dto.SessionResponseDto;
import ivan.adminpanel.dto.mapper.SessionMapper;
import ivan.adminpanel.model.User;
import ivan.adminpanel.service.SessionService;
import ivan.adminpanel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final UserService userService;
    private final SessionMapper sessionMapper;

    @GetMapping
    public ResponseEntity<List<SessionResponseDto>> getAll() {
        return new ResponseEntity<>(sessionService.getAll()
                .stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        sessionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<SessionResponseDto>> getByOwner(Authentication authentication,
                                                               Pageable pageable) {
        String userLogin = authentication.getName();
        String owner = userService.getByLogin(userLogin).getOwner();

        return new ResponseEntity<>(sessionService.getByOwner(owner, pageable)
                .stream().map(sessionMapper::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        sessionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
