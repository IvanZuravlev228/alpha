package ivan.adminpanel.controller;

import ivan.adminpanel.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final SessionService sessionService;

    @GetMapping("/files/session/{id}")
    public ResponseEntity<ByteArrayResource> downloadSessionFile(@PathVariable String id) throws IOException {
        String filePath = sessionService.getSessionUrlById(id);
        return getDataFile(filePath, id);
    }

    @GetMapping("/files/auth/{id}")
    public ResponseEntity<ByteArrayResource> downloadAuthFile(@PathVariable String id) throws IOException {
        String filePath = sessionService.getAuthUrlById(id);
        return getDataFile(filePath, id);
    }

    private ResponseEntity<ByteArrayResource> getDataFile(String filePath, String id) throws IOException {
        Path path = Paths.get(filePath);
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        sessionService.update(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
