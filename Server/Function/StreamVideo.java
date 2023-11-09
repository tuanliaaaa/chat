package Function;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import DTO.Dto;
import ShareData.ListUserOnline;

public class StreamVideo extends Thread{
     private Socket connectionSocket;
    private static Lock lock;
    private Dto receivedObject;
    private ListUserOnline listUserOnline;
    private Long conservationID;

    public StreamVideo(Socket connectionSocket,Dto receivedObject,Long conservationID,ListUserOnline listUserOnline)
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
            body.put("command", "streamVideo");
            body.put("conservationID",conservationID);
            body.put("video",receivedObject.getBody().get("video"));
            dto.setBody(body);
            try{
                ObjectOutputStream outToServer = new ObjectOutputStream(connectionSocket.getOutputStream());
                outToServer.writeObject(dto);
                
            }catch(SocketException se){
               
                List<Long> usListCall= listUserOnline.getUserCalling();
                usListCall.remove(Long.parseLong(receivedObject.getHeader().get("Token").toString()));
                listUserOnline.setUserCalling(usListCall);
                connectionSocket.close();   
                List<Socket> scList=listUserOnline.getSocket();
                scList.remove(connectionSocket);
                listUserOnline.setSocket(scList);
            }
            
        } catch (Exception se) {
            
            System.out.println("Client connection was aborted by the softwarex.");
        }
    }
}
