/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.time.LocalDateTime;

import Service.ConversationsPartService;
import Service.MessageService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author lethi
 */
@Data
@AllArgsConstructor
public class Message {
    private Long messageID;
    private String statusID;
    private Long usersendID;
    private Long conversationsID;
    private LocalDateTime createTime;
    public Message(String statusID,Long usersendID,Long conversationsID,LocalDateTime createTime)
    {
        this.statusID=statusID;
        this.usersendID=usersendID;
        this.conversationsID=conversationsID;
        this.createTime=createTime;
        MessageService messageService = new MessageService();
        if(messageService.getAll().size()>0)
        this.messageID=messageService.getAll().get(messageService.getAll().size()-1).getMessageID()+1;
        else this.messageID=0L;
    }
}
