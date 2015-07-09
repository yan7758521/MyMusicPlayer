package com.scu.mymusicplayer.bean;

import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.scu.mymusicplayer.application.MyApplication;

public class Db_music {
	private static MyApplication myApplication = MyApplication.getInstance();

	private static List<MusicInfo> musicLists = null;
	

	public List<MusicInfo> getAllMusic() {

		DbUtils db = DbUtils.create(myApplication.getApplicationContext());
		db.configAllowTransaction(true);
		db.configDebug(true);

		try {
			musicLists = db.findAll(MusicInfo.class);

		} catch (DbException e) {

		}
		return musicLists;
	}

	public void addMusic(MusicInfo musicInfo) {

		DbUtils db = DbUtils.create(myApplication.getApplicationContext());
		db.configAllowTransaction(true);
		db.configDebug(true);

		try {
			db.save(musicInfo);

		} catch (DbException e) {

		}

	}
	public MusicInfo findByMusicId(int i){
		MusicInfo musicInfo=null;
		DbUtils db = DbUtils.create(myApplication.getApplicationContext());
		db.configAllowTransaction(true);
		db.configDebug(true);
		try {
			musicInfo=db.findById(MusicInfo.class, i);
		} catch (DbException e) {
			e.printStackTrace();
			//musicInfo=db.findById(MusicInfo.class, 1);
		}
		return musicInfo;
		
	}

	public void deleteMusic(MusicInfo musicInfo) {

		DbUtils db = DbUtils.create(myApplication.getApplicationContext());
		db.configAllowTransaction(true);
		db.configDebug(true);

		try {
			db.delete(musicInfo);

		} catch (DbException e) {

		}

	}

}
