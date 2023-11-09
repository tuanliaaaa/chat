/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Service.TextService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author lethi
 */
@Data
@AllArgsConstructor
public class Text {
    private Long textID;
    private Long messageID;
    private String textContent;
    public Text(Long messageID, String textContent){
        this.messageID=messageID;
        this.textContent=textContent;
        TextService textService = new TextService();
        if(textService.getAll().size()>0)
        this.textID=textService.getAll().get(textService.getAll().size()-1).getTextID()+1;
        else this.textID=0L;
    }
}
