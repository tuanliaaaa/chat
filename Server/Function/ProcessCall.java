package Function;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import DTO.Dto;
import Entity.ConversationsParticipants;
import Entity.Message;
import Entity.Text;
import Entity.User;
import Entity.Video;
import Service.ConversationsPartService;
import Service.ConversationsService;
import Service.MessageService;
import Service.TextService;
import Service.UserService;
import Service.VideoService;
import ShareData.ListUserOnline;

public class ProcessCall extends Thread{
    private static Socket connectionSocket;
    private static UserService userService;
    private static ConversationsService conversationsService;
    private static ConversationsPartService conversationsPartService;
    private static MessageService messageService;
    private static TextService textService;
    private static VideoService videoService;
    private static Lock lock;
    private static ListUserOnline listUserOnline;
    private static Dto receivedObject;
    public ProcessCall(Socket connectionSocket,ListUserOnline listUserOnline,Lock lock,Dto receivedObject) {
        this.connectionSocket = connectionSocket;
        this.userService = new UserService();
        this.listUserOnline =listUserOnline;
        this.conversationsService= new ConversationsService();
        this.conversationsPartService=new ConversationsPartService();
        this.messageService= new MessageService();
        this.textService= new TextService();
        this.videoService = new VideoService();
        this.lock=lock;
        this.receivedObject=receivedObject;
    }
    @Override
    public void start() {
        try {

            Long userID = Long.parseLong(receivedObject.getHeader().get("Token").toString()); 
            User userLogin = userService.findById(userID);  
            User userLoginx = userService.findById(userID);  
            User userLoginy = userService.findById(userID);  
            User userLoginz = userService.findById(userID);
            Long conservationID=Long.parseLong(receivedObject.getBody().get("conservationID").toString());
            ConversationsParticipants conversationsParticipant = new ConversationsParticipants(userID,conservationID);
            Message message=messageService.save(new Message("0",userID,conservationID,LocalDateTime.now()));
            Video video= videoService.save(new Video(message.getMessageID()));
            List<ConversationsParticipants> conversationsParticipants = conversationsPartService.findByConversationId(conservationID);
            List<Long> usIDList = new ArrayList<>();
            for (ConversationsParticipants conversationsPart : conversationsParticipants) {
                usIDList.add(conversationsPart.getUserID());
                System.out.println(conversationsPart.getUserID());
            }
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Gửi Cuộc gọi thành công");
            body.put("command", "MessageProcess");
            body.put("statusCommand", "đang gọi");
            body.put("status", 201);
            dto.setBody(body);
            synchronized(listUserOnline){

            
                try{
                    ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                    outToServer.writeObject(dto);
                    List<Long> usListCalling = listUserOnline.getUserCalling();
                    usListCalling.add(userID);
                    listUserOnline.setUserCalling(usListCalling);
                }catch(Exception se){
                        connectionSocket.close();   
                        List<Socket> scList=listUserOnline.getSocket();
                        scList.remove(connectionSocket);
                        listUserOnline.setSocket(scList);
                }
                
                
                for(Long usID:usIDList)
                {
                    
                    for(int i=0;i<listUserOnline.getUsIDList().size();i++)
                    {

                        if(usID==listUserOnline.getUsIDList().get(i)&&usID!=userID&&!listUserOnline.getUserCalling().contains(usID)){
                            lock.lock();
                            try{
                                VideoCall vsCall =new VideoCall(listUserOnline.getSocket().get(i),listUserOnline,receivedObject,conservationID);
                                vsCall.start();
                            }finally{
                                lock.unlock();
                            }
                        }
                    }
                }
            }
            System.out.println("close luong yeu cau goi tong");
        } catch (Exception e) {
            System.out.println("bug ProcessCall");
           e.printStackTrace();
        }
    }
    
}
