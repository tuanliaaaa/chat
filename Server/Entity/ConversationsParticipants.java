/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Service.ConversationsPartService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author lethi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationsParticipants {
    private Long ConversationsParticipantsID;
    private Long userID;
    private Long conversationsID;
    public ConversationsParticipants(Long userID,Long conversationsID)
    {
        this.userID=userID;
        this.conversationsID=conversationsID;
        ConversationsPartService conversationsPartService = new ConversationsPartService();
        if(conversationsPartService.getAll().size()>0)
        this.ConversationsParticipantsID=conversationsPartService.getAll().get(conversationsPartService.getAll().size()-1).getConversationsParticipantsID()+1;
        else this.ConversationsParticipantsID=0L;
    }
}
