package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Entity.User;
import lombok.NoArgsConstructor;

public class UserService {
    private String filePath;

    public UserService() {
        String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/User.csv";
        this.filePath = filePath;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Long userID = Long.parseLong(parts[0]);
                    String username = parts[1];
                    String password = parts[2];
                    User user = new User(userID, username, password);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findById(Long id) {
        List<User> users = getAll();
        for (User user : users) {
            if (user.getUserID() == id) {
                return user;
            }
        }
        return null;
    }
    public User findByUsernameAndPassword(String username,String password) {
        List<User> users = getAll();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password )) {
                return user;
            }
        }
        return null;
    }
    public void save(User user) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String userString = user.getUserID() + "," + user.getUsername() + "," + user.getPassword() + "\n";
            writer.write(userString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
