import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import DTO.Dto;
import Entity.User;
import Function.Conservation;
import Function.Login;
import Function.VideoCall;
import Service.UserService;
import ShareData.ListUserOnline;

public class Router {
    private Socket connectionSocket;
    private ListUserOnline listUserOnline;
    private UserService userService;
    private Lock lock = new ReentrantLock();
    public Router(Socket connectionSocket,ListUserOnline listUserOnline) {
        this.connectionSocket = connectionSocket;
        this.listUserOnline=listUserOnline;
        this.userService = new UserService();
    }

    public void start() {
        try {
            // Đọc đối tượng từ client
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            
            // Đọc đối tượng từ client
            Dto receivedObject = (Dto) inFromClient.readObject();

            //Thêm user đang đăng nhập vào danh sách
            if (receivedObject.getHeader().containsKey("Token")) {
                Long userID = Long.parseLong(receivedObject.getHeader().get("Token").toString());
                userService.findById(userID);
                if(userService!=null){
                    List<Long> newusList=listUserOnline.getUsIDList();
                    
                    newusList.add(userID);
                    listUserOnline.setUsIDList(newusList);
                    List<Socket> newscList= listUserOnline.getSocket();
                    newscList.add(connectionSocket);
                    listUserOnline.setSocket(newscList);
                }
            }
            
            //Chia router
            if (receivedObject.getHeader().containsKey("function")) {
                String function= receivedObject.getHeader().get("function").toString();
                if(function.equals("Login")){
                    Login lg = new Login(connectionSocket,receivedObject);
                    lg.start();
                }else if(function.equals("conservations")){
                    Dto dto = new Dto();
                    Map<String, Object> header = new HashMap<>();
                    dto.setHeader(header);
                    Map<String, Object> body = new HashMap<>();
                    if(userService!=null){
                        body.put("message","Đã kết nối thành công");
                        body.put("status",200);
                    }else{
                        body.put("message","Vui lòng đăng nhập");
                        body.put("status",403);
                    }
                    dto.setBody(body);
                    ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                    // Gửi đối tượng đến server
                    outToServer.writeObject(dto);
                   
                    Conservation cs = new Conservation(connectionSocket,listUserOnline,lock);
                    cs.start();
                   
                    
                }else{
                    Dto dto = new Dto();
                    Map<String, Object> header = new HashMap<>();
                    dto.setHeader(header);
                    Map<String, Object> body = new HashMap<>();
                    body.put("message","Hàm không tồn tại");
                    body.put("status",404);
                    dto.setBody(body);
                    ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                    // Gửi đối tượng đến server
                    outToServer.writeObject(dto);
                    connectionSocket.close();
                }
            }else{ 
                Dto dto = new Dto();
                Map<String, Object> header = new HashMap<>();
                dto.setHeader(header);
                Map<String, Object> body = new HashMap<>();
                body.put("message","Vui lòng nhập hàm");
                body.put("status",400);
                dto.setBody(body);
                ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                // Gửi đối tượng đến server
                outToServer.writeObject(dto);
                connectionSocket.close();
            }
            
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}
