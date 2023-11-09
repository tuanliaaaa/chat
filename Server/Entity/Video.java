/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Service.TextService;
import Service.VideoService;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author lethi
 */
@Data
@AllArgsConstructor
public class Video {
    private Long videoID;
    private Long messageID;
    public Video(Long messageID){
        this.messageID=messageID;
       
        VideoService videoService = new VideoService();
        if(videoService.getAll().size()>0)
        this.videoID=videoService.getAll().get(videoService.getAll().size()-1).getVideoID()+1;
        else this.videoID=0L;
    }
}
