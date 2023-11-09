package Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import Entity.Video;

public class VideoService {
    private String filePath;

    public VideoService() {
        String filePath= Paths.get("").toAbsolutePath().toString() + "/Server" + "/Database/Video.csv";
        this.filePath = filePath;
    }

    public List<Video> getAll() {
        List<Video> videos = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    Long videoID = Long.parseLong(parts[0]);
                    Long messageID = Long.parseLong(parts[1]);
                    Video video = new Video(videoID, messageID);
                    videos.add(video);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public Video findById(Long id) {
        List<Video> videos = getAll();
        for (Video video : videos) {
            if (video.getVideoID() == id) {
                return video;
            }
        }
        return null;
    }

    public Video save(Video video) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String videoString = video.getVideoID() + "," + video.getMessageID() + "\n";
            writer.write(videoString);
            return video;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
