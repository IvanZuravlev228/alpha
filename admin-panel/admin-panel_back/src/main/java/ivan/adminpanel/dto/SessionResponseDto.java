package ivan.adminpanel.dto;

import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
public class SessionResponseDto {
    private String id;
    private String uploadTime;
    private String fileName;
    private String isDownload;
    private String owner;
    private String haveAuth;
}
