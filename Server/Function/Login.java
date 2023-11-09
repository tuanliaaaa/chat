package Function;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import DTO.Dto;
import Entity.User;
import Service.UserService;

public class Login {
    private static Socket connectionSocket;
    private static UserService userService;
    private static Dto receivedObject;
    public Login(Socket connectionSocket,Dto receivedObject) {
        this.connectionSocket = connectionSocket;
        this.userService = new UserService();
        this.receivedObject=receivedObject;
    }
    public static void start(){
        try {
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            // Đọc đối tượng từ client
            if (receivedObject.getBody().containsKey("username")) {
                String username= receivedObject.getBody().get("username").toString();

                if(receivedObject.getBody().containsKey("password")){
                    String password= receivedObject.getBody().get("username").toString();
                    User user = userService.findByUsernameAndPassword(username,password);
                    System.out.println(user);
                    if(user==null){
                        body.put("message", "tài khoản hoặc mật khẩu không chính xác");
                        body.put("status", 400);
                    }else{
                        body.put("userID", user.getUserID());
                        body.put("status", 200);
                    }
                }
            }
            dto.setBody(body);
            ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());

            // Gửi đối tượng đến server
            outToServer.writeObject(dto);

            connectionSocket.close();
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
        
    
}
