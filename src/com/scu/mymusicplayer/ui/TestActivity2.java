package com.scu.mymusicplayer.ui;

import com.scu.mymusicplayer.R;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity2 extends Activity{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musiclist_net);
		Bundle myBundle=this.getIntent().getExtras();
		String name =myBundle.getString("SearchKey");
		System.out.println(name);
		
	}
	
	

}
