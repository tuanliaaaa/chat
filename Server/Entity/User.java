package Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Long userID;
    private String username;
    private String password;
}
