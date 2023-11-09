package ShareData;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ListUserOnline {
    private List<Socket> Socket;
    private List<Long> usIDList;
    private List<Long> userCalling;
    public ListUserOnline(){
        this.Socket=new ArrayList();
        this.usIDList= new ArrayList();
        this.userCalling = new ArrayList<>();
    }
    public void setSocket(List<Socket> socket) {
        Socket = socket;
    }
    public List<Socket> getSocket() {
        return Socket;
    }
    public void setUsIDList(List<Long> usIDList) {
        this.usIDList = usIDList;
    }
    public List<Long> getUsIDList() {
        return usIDList;
    }
    public List<Long> getUserCalling() {
        return userCalling;
    }
    public void setUserCalling(List<Long> userCalling) {
        this.userCalling = userCalling;
    }
}
