package ivan.adminpanel.dto.mapper;

import ivan.adminpanel.dto.SessionResponseDto;
import ivan.adminpanel.model.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {
    public SessionResponseDto toDto (Session model) {
        SessionResponseDto dto = new SessionResponseDto();
        dto.setId(model.getId());
        dto.setFileName(model.getFileName());
        dto.setUploadTime(model.getUploadTime());
        dto.setIsDownload(model.getIsDownload());
        dto.setOwner(model.getOwner());
        dto.setHaveAuth(model.getAuthURL().isEmpty() ? "no" : "yes");
        return dto;
    }
}
