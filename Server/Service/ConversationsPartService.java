
package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Entity.ConversationsParticipants;
public class ConversationsPartService {
    private String filePath;

    public ConversationsPartService() {
         String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/ConversationsPart.csv";
        this.filePath = filePath;
    }

    public List<ConversationsParticipants> getAll() {
        List<ConversationsParticipants> conversationsParticipants = new ArrayList<>();
        try  {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Long ConversationsParticipantsID = Long.parseLong(parts[0]);
                    Long userID = Long.parseLong(parts[1]);
                    Long conversationsID = Long.parseLong(parts[2]);
                    ConversationsParticipants conversationsParticipant = new ConversationsParticipants(ConversationsParticipantsID, userID, conversationsID);
                    conversationsParticipants.add(conversationsParticipant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conversationsParticipants;
    }

    public ConversationsParticipants findById(Long id) {
        List<ConversationsParticipants> conversationsParticipants = getAll();
        for (ConversationsParticipants conversationsParticipant : conversationsParticipants) {
            if (conversationsParticipant.getConversationsParticipantsID() == id) {
                return conversationsParticipant;
            }
        }
        return null;
    }
    public List<ConversationsParticipants> findByConversationId(Long conversationid) {
        List<ConversationsParticipants> conversationsParticipants = getAll();
        List<ConversationsParticipants> conversationsParticipantList = new ArrayList<>();
        for (ConversationsParticipants conversationsParticipant : conversationsParticipants) {
            if (conversationsParticipant.getConversationsID() == conversationid) {
                conversationsParticipantList.add(conversationsParticipant);
            }
        }
        return conversationsParticipantList;
    }
    public void save(ConversationsParticipants conversationsParticipants) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String ConversationsParticipantsString = conversationsParticipants.getConversationsParticipantsID() + "," + conversationsParticipants.getUserID()+ "," + conversationsParticipants.getConversationsID() + "\n";
            writer.write(ConversationsParticipantsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
