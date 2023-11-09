package View;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;

import DTO.Dto;
public class Client1 {
    public static void main(String argv[]) {
        try {
            Socket clientSocket = new Socket("localhost", 2207);

            // gửi lần 1
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            header.put("function", "conservations");
            header.put("Token",2);
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            dto.setBody(body);
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer.writeObject(dto);
            //nhận về thành công kết nối
            ObjectInputStream inFromClient0 = new ObjectInputStream(clientSocket.getInputStream());
            Dto receivedObject0 = (Dto) inFromClient0.readObject();
            System.out.println(receivedObject0);

            // gửi lần 2
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
            ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer2.writeObject(dto2);

            //nhận lần 2:
            ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
            Dto receivedObject = (Dto) inFromClient.readObject();
            System.out.println(receivedObject);
            //nhận lần 3:
            ObjectInputStream inFromClientxx = new ObjectInputStream(clientSocket.getInputStream());
            Dto receivedObjectxx = (Dto) inFromClientxx.readObject();
            System.out.println(receivedObjectxx);
            //nhận lần 4:
            ObjectInputStream inFromClient4 = new ObjectInputStream(clientSocket.getInputStream());
            Dto receivedObject4 = (Dto) inFromClient4.readObject();
            System.out.println(receivedObject4);
            //gửi lần 3:
            Dto dto3 = new Dto();
            Map<String, Object> header3 = new HashMap<>();
            header3.put("function", "conservations");
            header3.put("Token",2);
            dto3.setHeader(header3);
            Map<String, Object> body3 = new HashMap<>();
            body3.put("command", "authCallVideo");
            body3.put("conservationID", 1);
            body3.put("statusAuthCall", "chấp nhận cuộc gọi");
            body3.put("content","tao là 2");
            dto3.setBody(body3);
            ObjectOutputStream outToServer3 = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer3.writeObject(dto3);
            //gửi lần 4
            Dto dto5 = new Dto();
            Map<String, Object> header5 = new HashMap<>();
            header5.put("function", "conservations");
            header5.put("Token",2);
            dto5.setHeader(header5);
            Map<String, Object> body5 = new HashMap<>();
            body5.put("command", "Message");
            body5.put("conservationID", 1);
            body5.put("content","tao là 2");
           
            dto5.setBody(body5);
            ObjectOutputStream outToServer5 = new ObjectOutputStream(clientSocket.getOutputStream());
            // Gửi đối tượng đến server
            outToServer5.writeObject(dto5);
            while (true) {
                ObjectInputStream inFromClienxt = new ObjectInputStream(clientSocket.getInputStream());
                Dto receivedObjectx = (Dto) inFromClienxt.readObject();
                System.out.println(receivedObjectx);             
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

