package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Entity.UserRole;
public class UserRoleService {
    private String filePath;

    public UserRoleService() {
        String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/UserRole.csv";
        this.filePath = filePath;
    }

    public List<UserRole> getAll() {
        List<UserRole> userroles = new ArrayList<>();
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Long userID = Long.parseLong(parts[1]);
                    Long userRoleID = Long.parseLong(parts[0]);
                    Long roleID = Long.parseLong(parts[2]);
                    UserRole userrole = new UserRole(userRoleID ,userID, roleID);
                    userroles.add(userrole);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userroles;
    }

    public UserRole findById(Long id) {
        List<UserRole> userroles = getAll();
        for (UserRole userrole : userroles) {
            if (userrole.getUserRoleID() == id) {
                return userrole;
            }
        }
        return null;
    }

    public void save(UserRole userrole) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String userRoleString = userrole.getUserRoleID() + "," + userrole.getUserID() + "," + userrole.getRoleID() + "\n";
            writer.write(userRoleString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
