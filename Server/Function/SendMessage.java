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

public class SendMessage extends Thread{
    private Socket connectionSocket;
    private static Lock lock;
    private Dto receivedObject;
    private ListUserOnline listUserOnline;

    public SendMessage(Socket connectionSocket,Dto receivedObject)
    {
        this.connectionSocket= connectionSocket;
        this.receivedObject=receivedObject;
    }
    @Override
    public void run() {
        
        try{     
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            body.put("from",Long.parseLong(receivedObject.getHeader().get("Token").toString()));
            body.put("nội dung", receivedObject.getBody().get("content").toString());
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
            
        } catch (Exception se) {
            
            System.out.println("Client connection was aborted by the software.");
        }
        System.out.println("close message i");
    }
}
