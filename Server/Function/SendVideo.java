package Function;

import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import DTO.Dto;
import ShareData.ListUserOnline;

public class SendVideo extends Thread{
    private Socket connectionSocket;
    private static Lock lock;
    
    private ListUserOnline listUserOnline;
    private BufferedImage image;
    public SendVideo(Socket connectionSocket,BufferedImage image)
    {
        this.connectionSocket= connectionSocket;
        this.image=image;
    }
    @Override
    public void run() {
        
        try{     
            Dto dto = new Dto();
            Map<String, Object> header = new HashMap<>();
            dto.setHeader(header);
            Map<String, Object> body = new HashMap<>();
            body.put("from","1");
            body.put("nội dung", "chó ngọc");
            body.put("stauts",200);
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
    }
}
