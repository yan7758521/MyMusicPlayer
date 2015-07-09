package com.scu.mymusicplayer.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.bean.Db_music;
import com.scu.mymusicplayer.bean.MusicInfo;
import com.scu.mymusicplayer.view.ClearEditText;

public class TestActivity extends Activity {
	private Button button1;
	private Button button2;
	ClearEditText clearEditText;
	private Db_music db_music;
	private static List<MusicInfo> list;
	
	private static MusicInfo musicInfo=new MusicInfo(23, "dasd", "da", "dasd", 24324);
	private static MusicInfo musicInfo1=new MusicInfo(23, "dasd", "da", "dasd", 24324);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		db_music=new Db_music();
		clearEditText=(ClearEditText) findViewById(R.id.et_msg_search);
		button1=(Button) findViewById(R.id.bnt_addMusic);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db_music.addMusic(musicInfo);
				db_music.addMusic(musicInfo1);
				
				
			}
		});
		
		button2=(Button) findViewById(R.id.bnt_getMusic);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				list=db_music.getAllMusic();

				for(int i =0 ;i<list.size();i++){
					System.out.println(list.get(i).toString());
				}
				
				
				
			}
		});
		
		
		
	}
	

}
