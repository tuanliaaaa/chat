package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Entity.Message;

public class MessageService {
    private String filePath;

    public MessageService() {
        String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/Message.csv";
        this.filePath = filePath;
    }

    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Long messageID = Long.parseLong(parts[0]);
                    String statusID = parts[1];
                    Long usersendID = Long.parseLong(parts[2]);
                    Long conversationsID = Long.parseLong(parts[3]);
                    LocalDateTime createTime = LocalDateTime.parse(parts[4]);
                    Message message = new Message(messageID, statusID, usersendID, conversationsID, createTime);
                    messages.add(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Message findById(Long id) {
        List<Message> messages = getAll();
        for (Message message : messages) {
            if (message.getMessageID() == id) {
                return message;
            }
        }
        return null;
    }

    public Message save(Message message) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String messageString = message.getMessageID() + "," + message.getStatusID() + "," + message.getUsersendID() + ","
                    + message.getConversationsID() + "," + message.getCreateTime() + "\n";
            writer.write(messageString);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
