package ivan.adminpanel.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "sessions")
public class Session {
    @Id
    private String id;
    private String fileURL;
    private String authURL;
    private String fileName;
    private String uploadTime;
    private String isDownload;
    private String owner;
}
