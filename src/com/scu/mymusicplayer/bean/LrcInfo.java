package com.scu.mymusicplayer.bean;

import java.util.List;
import java.util.Map;
/*  
 * 用于封装歌词的类  
 * @author  
 *   
 * */  
public class LrcInfo {  
    private String title;//music title  
    private String artist;//artist name  
    private String album;//album name  
    private String bySomeBody;//the lrc maker  
    private String offset;//the time delay or bring forward  
    private Map<Long,String> infos;//保存歌词信息和时间点�?��对应的Map  
    
    private List<Long> times;
    private List<String> texts;
    public List<Long> getTimes() {
		return times;
	}
	public void setTimes(List<Long> times) {
		this.times = times;
	}
	public List<String> getTexts() {
		return texts;
	}
	public void setTexts(List<String> texts) {
		this.texts = texts;
	}
	public String getTitle() {  
        return title;  
    }  
    public void setTitle(String title) {  
        this.title = title;  
    }  
    public String getArtist() {  
        return artist;  
    }  
    public void setArtist(String artist) {  
        this.artist = artist;  
    }  
    public String getAlbum() {  
        return album;  
    }  
    public void setAlbum(String album) {  
        this.album = album;  
    }  
    public String getBySomeBody() {  
        return bySomeBody;  
    }  
    public void setBySomeBody(String bySomeBody) {  
        this.bySomeBody = bySomeBody;  
    }  
    public String getOffset() {  
        return offset;  
    }  
    public void setOffset(String offset) {  
        this.offset = offset;  
    }  
    public Map<Long, String> getInfos() {  
        return infos;  
    }  
    public void setInfos(Map<Long, String> infos) {  
        this.infos = infos;  
    }  
      
}  
