package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Entity.Text;

public class TextService {
    private String filePath;

    public TextService() {
        String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/Text.csv";
        this.filePath = filePath;
    }

    public List<Text> getAll() {
        List<Text> texts = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Long textID = Long.parseLong(parts[0]);
                    Long messageID = Long.parseLong(parts[1]);
                    String textContent = parts[2];
                    Text text = new Text(textID, messageID, textContent);
                    texts.add(text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texts;
    }

    public Text findById(Long id) {
        List<Text> texts = getAll();
        for (Text text : texts) {
            if (text.getTextID() == id) {
                return text;
            }
        }
        return null;
    }

    public Text save(Text text) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String textString = text.getTextID() + "," + text.getMessageID() + "," + text.getTextContent() + "\n";
            writer.write(textString);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
