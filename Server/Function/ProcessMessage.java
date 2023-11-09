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
import Service.ConversationsPartService;
import Service.ConversationsService;
import Service.MessageService;
import Service.TextService;
import Service.UserService;
import Service.VideoService;
import ShareData.ListUserOnline;

public class ProcessMessage extends Thread{
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
    public ProcessMessage(Socket connectionSocket,ListUserOnline listUserOnline,Lock lock,Dto receivedObject) {
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
    public void run() {
        try {
            Long userID = Long.parseLong(receivedObject.getHeader().get("Token").toString());
            User userLogin = userService.findById(userID);  
            Long conservationID=Long.parseLong(receivedObject.getBody().get("conservationID").toString());
            String content =receivedObject.getBody().get("content").toString();
            ConversationsParticipants conversationsParticipant = new ConversationsParticipants(userID,conservationID);
            Message message=messageService.save(new Message("0",userID,conservationID,LocalDateTime.now()));
            Text text= textService.save(new Text(message.getMessageID(), content));
            List<ConversationsParticipants> conversationsParticipants = conversationsPartService.findByConversationId(conservationID);
            List<Long> usIDList = new ArrayList<>();
            for (ConversationsParticipants conversationsPart : conversationsParticipants) {
                usIDList.add(conversationsPart.getUserID());
            }
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Gửi Tin nhắn thành công");
            body.put("command", "MessageProcess");
            body.put("status", 201);
            dto.setBody(body);
            
            try{
                ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                outToServer.writeObject(dto);
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

                    if(usID==listUserOnline.getUsIDList().get(i)){
                        
                        SendMessage sm =new SendMessage(listUserOnline.getSocket().get(i),receivedObject);
                        sm.start();
                    }
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
