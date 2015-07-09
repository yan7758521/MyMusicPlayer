package com.scu.mymusicplayer.bean;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
import com.scu.mymusicplayer.util.PinyinComparator;
@Table(name="musicInfo")
public class MusicInfo extends PinyinComparator implements Serializable{
	/**
	 * 
	 */
	@Id
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(column = "m_musicId")
	private int m_musicId;
	@Column (column = "m_musicPath")
	private String m_musicPath;
	@Column (column = "m_musicTitle")
	private String m_musicTitle;
	@Column (column = "m_musicSinger")
	private String m_musicSinger;
	@Column (column = "m_duration")
	private int m_duration;
	private String sortLetters;
	
	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	
	public MusicInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MusicInfo(Integer musicId, String musicPath, String musicTitle, String musicSinger, int duration){
		this.m_musicId = musicId;
		this.m_musicPath = musicPath;
		this.m_musicTitle = musicTitle;
		this.m_musicSinger = musicSinger;
		this.m_duration = duration;
	}
	
	

    public MusicInfo(int id, int m_musicId, String m_musicPath,
			String m_musicTitle, String m_musicSinger, int m_duration) {
		super();
		this.id = id;
		this.m_musicId = m_musicId;
		this.m_musicPath = m_musicPath;
		this.m_musicTitle = m_musicTitle;
		this.m_musicSinger = m_musicSinger;
		this.m_duration = m_duration;
	}

	public int getM_musicId() {
		return m_musicId;
	}

	public void setM_musicId(int m_musicId) {
		this.m_musicId = m_musicId;
	}

	public String getM_musicPath() {
		return m_musicPath;
	}

	public void setM_musicPath(String m_musicPath) {
		this.m_musicPath = m_musicPath;
	}

	public String getM_musicTitle() {
		return m_musicTitle;
	}

	public void setM_musicTitle(String m_musicTitle) {
		this.m_musicTitle = m_musicTitle;
	}

	public String getM_musicSinger() {
		return m_musicSinger;
	}

	public void setM_musicSinger(String m_musicSinger) {
		this.m_musicSinger = m_musicSinger;
	}

	public int getM_duration() {
		return m_duration;
	}

	public void setM_duration(int m_duration) {
		this.m_duration = m_duration;
	}

	@Override
	public String toString() {
		return "MusicInfo [id=" + id + ", m_musicId=" + m_musicId
				+ ", m_musicPath=" + m_musicPath + ", m_musicTitle="
				+ m_musicTitle + ", m_musicSinger=" + m_musicSinger
				+ ", m_duration=" + m_duration + ", sortLetters=" + sortLetters
				+ "]";
	}

	
	
}
