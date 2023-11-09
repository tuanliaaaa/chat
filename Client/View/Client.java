package View;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import DTO.Dto;
public class Client {
    public static void main(String argv[]) {
        try {
            Socket clientSocket = new Socket("localhost", 2207);
            //Gửi lần 1
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            header.put("function", "conservations");
            header.put("Token",1);
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            dto.setBody(body);
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer.writeObject(dto);
            //Nhận về lần 1
            ObjectInputStream inFromClient0 = new ObjectInputStream(clientSocket.getInputStream());
            Dto receivedObject0 = (Dto) inFromClient0.readObject();
            System.out.println(receivedObject0);

            // Gửi Lần 2
            Dto dto2 = new Dto();
            Map<String, Object> header2 = new HashMap<>();
            header2.put("Token",1);
            dto2.setHeader(header2);
            Map<String, Object> body2 = new HashMap<>();
            body2.put("command", "Message");
            body2.put("conservationID", 1);
            body2.put("content","tao là 1");
            dto2.setBody(body2);
            ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer2.writeObject(dto2);
            while (true) {
            
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                Dto receivedObject = (Dto) inFromClient.readObject();
                System.out.println(receivedObject);
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

