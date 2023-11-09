package Function;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

import DTO.Dto;
import Entity.ConversationsParticipants;
import Entity.Message;
import Entity.Text;
import Entity.User;
import Entity.Video;
import Service.*;
import ShareData.ListUserOnline;
import lombok.Synchronized;

public class Conservation extends Thread{
    private static Socket connectionSocket;
    private static UserService userService;
    private static ConversationsService conversationsService;
    private static ConversationsPartService conversationsPartService;
    private static MessageService messageService;
    private static TextService textService;
    private static VideoService videoService;
    private static Lock lock;

    private static ListUserOnline listUserOnline;
    public Conservation(Socket connectionSocket,ListUserOnline listUserOnline,Lock lock) {
        this.connectionSocket = connectionSocket;
        this.userService = new UserService();
        this.listUserOnline =listUserOnline;
        this.conversationsService= new ConversationsService();
        this.conversationsPartService=new ConversationsPartService();
        this.messageService= new MessageService();
        this.textService= new TextService();
        this.videoService = new VideoService();
        this.lock= lock;
    }
    @Override
    public void run() {
            
        while (true) {     
                try {   
                    ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
                    Dto receivedObject = (Dto) inFromClient.readObject(); 
                    System.out.println(receivedObject);
                    if(receivedObject.getBody().containsKey("command"))
                    {
                        String cmd=receivedObject.getBody().get("command").toString();
                    
                        if(cmd.equals("Message"))
                        {    
                            ProcessMessage pm = new ProcessMessage(connectionSocket, listUserOnline, lock, receivedObject);
                            pm.start(); 
                        }else if(cmd.equals("callVideo")){
                                ProcessCall pc = new ProcessCall(connectionSocket, listUserOnline, lock, receivedObject);
                                pc.start();
                        
                        }
                        else if(cmd.equals("authCallVideo")){
                            ProcessAuthCall pcaAuthCall = new ProcessAuthCall(connectionSocket, listUserOnline, lock, receivedObject);
                            pcaAuthCall.start(); 
                        }else if(cmd.equals("streamVideo")){
                            
                            ProcessStreamVideo processStreamVideo = new ProcessStreamVideo(connectionSocket, listUserOnline, lock, receivedObject);
                            processStreamVideo.start();
                        }
                        else{
                        
                        }
                    }else{
                        
                        Dto dto = new Dto();
                        Map<String, Object> header = new HashMap<>();
                        dto.setHeader(header);
                        Map<String, Object> body = new HashMap<>();
                        body.put("message", "Vui lòng nhập command");
                        body.put("status", 400);
                        dto.setBody(body);
                        ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                        outToServer.writeObject(dto);
                    }
                
                } catch (Exception e) {
                    System.out.println("Bug Conservation");
                    e.printStackTrace();
                }
            }
        }
    
}
