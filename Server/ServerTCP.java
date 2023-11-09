import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import Function.Login;
import DTO.Dto;
import Entity.User;
import Service.UserService;
import ShareData.ListUserOnline;
public class ServerTCP {
    private static ListUserOnline listUserOnline;
    public static void router(Socket connectionSocket){
        
        Router rt = new Router(connectionSocket,listUserOnline);
        rt.start();
    } 
    public static void main(String argv[]) {
         listUserOnline = new ListUserOnline();
        try {
            ServerSocket serverSocket = new ServerSocket(2207);
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                router(connectionSocket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

