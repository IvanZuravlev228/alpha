package ivan.adminpanel.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    private String id;
    private String login;
    private String password;
    private String owner;
    private String role;
}
