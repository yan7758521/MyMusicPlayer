package com.scu.mymusicplayer.bean;

public class SongInfo {
	private String albumid;
	private String albumname;
	private String bigPicture_url;
	private String smallPicture_url;
	private String downUrl;
	private String singerName;
	private String songId;
	private String songName;
	public SongInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SongInfo(String albumid, String albumname, String bigPicture_url,
			String smallPicture_url, String downUrl, String singerName,
			String songId, String songName) {
		super();
		this.albumid = albumid;
		this.albumname = albumname;
		this.bigPicture_url = bigPicture_url;
		this.smallPicture_url = smallPicture_url;
		this.downUrl = downUrl;
		this.singerName = singerName;
		this.songId = songId;
		this.songName = songName;
	}
	public String getAlbumid() {
		return albumid;
	}
	public void setAlbumid(String albumid) {
		this.albumid = albumid;
	}
	public String getAlbumname() {
		return albumname;
	}
	public void setAlbumname(String albumname) {
		this.albumname = albumname;
	}
	public String getBigPicture_url() {
		return bigPicture_url;
	}
	public void setBigPicture_url(String bigPicture_url) {
		this.bigPicture_url = bigPicture_url;
	}
	public String getSmallPicture_url() {
		return smallPicture_url;
	}
	public void setSmallPicture_url(String smallPicture_url) {
		this.smallPicture_url = smallPicture_url;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getSingerName() {
		return singerName;
	}
	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}
	public String getSongId() {
		return songId;
	}
	public void setSongId(String songId) {
		this.songId = songId;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	@Override
	public String toString() {
		return "SongInfo [albumid=" + albumid + ", albumname=" + albumname
				+ ", bigPicture_url=" + bigPicture_url + ", smallPicture_url="
				+ smallPicture_url + ", downUrl=" + downUrl + ", singerName="
				+ singerName + ", songId=" + songId + ", songName=" + songName
				+ "]";
	}
	
	

}
