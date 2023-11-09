package Function;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

import DTO.Dto;
import Entity.ConversationsParticipants;
import Entity.User;
import Service.ConversationsPartService;
import Service.ConversationsService;
import Service.MessageService;
import Service.TextService;
import Service.UserService;
import Service.VideoService;
import ShareData.ListUserOnline;

public class ProcessStreamVideo extends Thread{
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
    public ProcessStreamVideo(Socket connectionSocket,ListUserOnline listUserOnline,Lock lock,Dto receivedObject) {
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
            System.out.println("vào Process Stream");
            Long userID = Long.parseLong(receivedObject.getHeader().get("Token").toString());
            List<Long> usIDList= new ArrayList<>();
            Long conservationID=Long.parseLong(receivedObject.getBody().get("conservationID").toString());            
            List<ConversationsParticipants> conversationsParticipants=conversationsPartService.findByConversationId(conservationID);
            for(ConversationsParticipants conversationsParticipant: conversationsParticipants){
                usIDList.add(conversationsParticipant.getUserID());
            }
            for(Long usID:usIDList)
            {
                
                for(int i=0;i<listUserOnline.getUsIDList().size();i++)
                {

                    if(usID==listUserOnline.getUsIDList().get(i)&&usID!=userID&&listUserOnline.getUserCalling().contains(usID)){
                        System.out.println("vào Process Stream với ID:"+usID);
                        lock.lock();
                        try{
                            StreamVideo streamVideo =new StreamVideo(listUserOnline.getSocket().get(i),receivedObject,conservationID,listUserOnline);
                            streamVideo.start();
                        }finally{
                            lock.unlock();
                        }
                    }
                }
            }
            
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
