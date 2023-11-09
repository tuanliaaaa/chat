package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Entity.Role;
public class RoleService {
    private String filePath;

    public RoleService() {
        String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/Role.csv";
        this.filePath = filePath;
    }

    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Long roleID = Long.parseLong(parts[0]);
                    String roleName = parts[1];
                    
                    Role role = new Role(roleID ,roleName );
                    roles.add(role);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public Role findById(Long id) {
        List<Role> roles = getAll();
        for (Role role : roles) {
            if (role.getRoleID() == id) {
                return role;
            }
        }
        return null;
    }

    public void save(Role role) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String roleString = role.getRoleID() + "," + role.getRoleName() + "\n";
            writer.write(roleString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

