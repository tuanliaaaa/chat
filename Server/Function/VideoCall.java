package Function;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
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

public class VideoCall extends Thread{
    private Socket connectionSocket;
    private static Lock lock;
    private Dto receivedObject;
    private ListUserOnline listUserOnline;
    private Long conservationID;

    public VideoCall(Socket connectionSocket,ListUserOnline listUserOnline,Dto receivedObject,Long conservationID)
    {
        this.connectionSocket= connectionSocket;
        this.receivedObject=receivedObject;
        this.conservationID= conservationID;
        this.listUserOnline=listUserOnline;
    }
    @Override
    public void run() {
        try{     
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            body.put("from",Long.parseLong(receivedObject.getHeader().get("Token").toString()));
            body.put("command", "đang nhận cuôc gọi");
            body.put("conservationID",conservationID);
            body.put("status",200);
            dto.setBody(body);
            
            try{
                ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                outToServer.writeObject(dto);
           
            }catch(SocketException se){
                connectionSocket.close();   
                List<Socket> scList=listUserOnline.getSocket();
                scList.remove(connectionSocket);
                listUserOnline.setSocket(scList);
            }
            System.out.println("close luong yeu cau goi người thứ : "+"x");
        } catch (Exception se) {
            
            System.out.println("Client connection was aborted by the software.");
        }
    }
}
