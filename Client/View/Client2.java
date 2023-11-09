package View;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


import DTO.Dto;
public class Client2 {
    public static void main(String argv[]) {
        try {
            Socket clientSocket = new Socket("localhost", 2207);
            //gửi lần 1
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            header.put("function", "conservations");
            header.put("Token",3);
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            body.put("command", "Message");
            body.put("conservationID", 1);
            body.put("content","tao là 3");
            dto.setBody(body);
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer.writeObject(dto);
            //nhận lần 1
            ObjectInputStream inFromClient0 = new ObjectInputStream(clientSocket.getInputStream());
            Dto receivedObject0 = (Dto) inFromClient0.readObject();
            System.out.println(receivedObject0);
             // gửi lần 2
            Dto dto2 = new Dto();
            Map<String, Object> header2 = new HashMap<>();
            header2.put("function", "conservations");
            header2.put("Token",3);
            dto2.setHeader(header2);
            Map<String, Object> body2 = new HashMap<>();
            body2.put("command", "callVideo");
            body2.put("conservationID", 1);
            body2.put("content","tao là 3");
            dto2.setBody(body2);
            ObjectOutputStream outToServer2 = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer2.writeObject(dto2);
     
            while (true) {
                    ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                    Dto receivedObject = (Dto) inFromClient.readObject();
                    System.out.println(receivedObject);
                    ObjectInputStream inFromClient1 = new ObjectInputStream(clientSocket.getInputStream());
                    Dto receivedObject1 = (Dto) inFromClient1.readObject();
                    System.out.println(receivedObject1);
                    Dto dto5 = new Dto();
                    Map<String, Object> header5 = new HashMap<>();
                    header5.put("function", "conservations");
                    header5.put("Token",3);
                    dto5.setHeader(header5);
                    Map<String, Object> body5 = new HashMap<>();
                    body5.put("command", "Message");
                    body5.put("conservationID", 1);
                    body5.put("content","tao là 3");
                   
                    dto5.setBody(body5);
                    ObjectOutputStream outToServer5 = new ObjectOutputStream(clientSocket.getOutputStream());
                    // Gửi đối tượng đến server
                    outToServer5.writeObject(dto5);
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

