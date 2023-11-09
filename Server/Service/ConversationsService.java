
package Service;
import java.sql.Time;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Entity.Conversations;
public class ConversationsService {
    private String filePath;

    public ConversationsService() {
        String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/Conversations.csv";
        this.filePath = filePath;
    }

    public List<Conversations> getAll() {
        List<Conversations> conversations = new ArrayList<>();
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Long conversationsID = Long.parseLong(parts[0]);
                    String conversationsName = parts[1];
                    Time createTime = Time.valueOf(parts[2]);
                    Conversations conversation = new Conversations(conversationsID, conversationsName, createTime);
                    conversations.add(conversation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conversations;
    }

    public Conversations findById(Long id) {
        List<Conversations> conversations = getAll();
        for (Conversations conversation : conversations) {
            if (conversation.getConversationsID() == id) {
                return conversation;
            }
        }
        return null;
    }

    public void save(Conversations conversations) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String ConversationsString = conversations.getConversationsID() + "," + conversations.getConversationName()+ "," + conversations.getCreateTime() + "\n";
            writer.write(ConversationsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
