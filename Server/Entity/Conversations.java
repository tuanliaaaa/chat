/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.sql.Time;

import Service.ConversationsPartService;
import Service.ConversationsService;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 *
 * @author lethi
 */
@Data
@AllArgsConstructor
public class Conversations {
    private Long conversationsID;
    private String conversationName;
    private Time createTime;
     public Conversations(String conversationName,Time createTime)
    {
        this.conversationName=conversationName;
        this.createTime=createTime;
        ConversationsService conversationsService = new ConversationsService();
        if(conversationsService.getAll().size()>0)
        this.conversationsID=conversationsService.getAll().get(conversationsService.getAll().size()-1).getConversationsID()+1;
        else this.conversationsID=0L;
    }
}
