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
import Entity.User;
import Entity.Video;
import Service.ConversationsPartService;
import Service.ConversationsService;
import Service.MessageService;
import Service.TextService;
import Service.UserService;
import Service.VideoService;
import ShareData.ListUserOnline;

public class ProcessAuthCall extends Thread{
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
    public ProcessAuthCall(Socket connectionSocket,ListUserOnline listUserOnline,Lock lock,Dto receivedObject) {
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
            List<Long> usIDList= listUserOnline.getUserCalling();
            Long conservationID=Long.parseLong(receivedObject.getBody().get("conservationID").toString());            
            
            for(Long usID:usIDList)
            {
                
                for(int i=0;i<listUserOnline.getUsIDList().size();i++)
                {

                    if(usID==listUserOnline.getUsIDList().get(i)&&usID!=userID&&listUserOnline.getUserCalling().contains(usID)){
                        lock.lock();
                        try{
                            AuthVideoCall authVideoCallvsCall =new AuthVideoCall(listUserOnline.getSocket().get(i),receivedObject,conservationID,listUserOnline);
                            authVideoCallvsCall.start();
                        }finally{
                            lock.unlock();
                        }
                    }
                }
            }
            System.out.println("Nhi");
            
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
