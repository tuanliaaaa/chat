package View;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

import DTO.Dto;

public class luong extends Thread{
    private Socket socket;
    private Webcam webcam;
    public luong(Socket socket, Webcam webcam){
        this.socket=socket;
        this.webcam=webcam;
    }
    @Override
    public void run() {
         try {
            while (true) {
                Dto dto2 = new Dto();
                Map<String, Object> header2 = new HashMap<>();
                header2.put("function", "conservations");
                header2.put("Token",2);
                dto2.setHeader(header2);
    
                Map<String, Object> body2 = new HashMap<>();
                body2.put("command", "Message");
                body2.put("conservationID", 1);
                body2.put("content","tao là 2");
                dto2.setBody(body2);
                ObjectOutputStream outToServer2 = new ObjectOutputStream(socket.getOutputStream());
    
                // Gửi đối tượng đến server
                outToServer2.writeObject(dto2);
                // Gửi đối tượng đến server
              
                // System.out.println("chịu");
                // Dto dto4 = new Dto();
                // Map<String, Object> header4 = new HashMap<>();
                
                // header4.put("Token",2);
                // dto4.setHeader(header4);

                // Map<String, Object> body4 = new HashMap<>();
                // body4.put("command", "streamVideo");
                // body4.put("conservationID", 1);
                
                // body4.put("content","tao là 2");
                // BufferedImage image = webcam.getImage();
                // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                // ImageIO.write(image, "jpg", byteArrayOutputStream);
                // body4.put("video", byteArrayOutputStream.toByteArray());
                // dto4.setBody(body4);
                // ObjectOutputStream outToServer4 = new ObjectOutputStream(socket.getOutputStream());
                // outToServer4.writeObject(dto4);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
