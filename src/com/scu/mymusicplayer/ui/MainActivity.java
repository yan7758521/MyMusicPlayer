package com.scu.mymusicplayer.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.bean.Db_music;
import com.scu.mymusicplayer.bean.MusicInfo;
import com.scu.mymusicplayer.fragment.BaseFragment.OnHeadlineSelectedListener;
import com.scu.mymusicplayer.fragment.MusicListFragment;
import com.scu.mymusicplayer.fragment.MusicPlayingFragment;
import com.scu.mymusicplayer.fragment.SetFrament;
import com.scu.mymusicplayer.view.HeaderLayout;

public class MainActivity extends FragmentActivity implements
		OnHeadlineSelectedListener, OnGestureListener {
	private Button[] mTabs;
	private MusicListFragment musicListFragment;
	private MusicPlayingFragment musicPlayingFragment;
	private SetFrament setFrament;
	private static Fragment[] fragments;
	private static LinearLayout[] linearLayouts;
	public static TextView[] textViews;
	public static GestureDetector detector;
	private static Db_music db_music = new Db_music();
	final int DISTANT = 50;

	// public List<Fragment> myFragments = new ArrayList<Fragment>();
	private int index ;
	private int currentTabIndex=1;
	private ImageView list_tips;
	private ImageView playing_tips;

	private static int loadFlag = 1;

	private MusicInfo nowMusic;
	private List<MusicInfo> MymusicList;

	private ViewPager viewPager;
	public static final String PREFS_NAME = "MyPrefsFile";
	public static final String FIRST_RUN = "first";
	private boolean first;
	private static FragmentManager mFragmentMan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		mFragmentMan = getSupportFragmentManager();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		first = settings.getBoolean(FIRST_RUN, true);
		if (first) {
			getMusicList();
			addToDb(MymusicList);
			System.out.println("first time");
		} else {
			System.out.println("not first time");
		}
		initView2();
		initTab();
		detector = new GestureDetector(this);

	}

	private void addToDb(List<MusicInfo> musicList2) {
		for (int i = 0; i < musicList2.size(); i++) {
			db_music.addMusic(musicList2.get(i));
		}
		System.out.println("数据库添加完毕");

	}

	public void getMusicList() {
		if (loadFlag == 1) {
			Cursor mAudioCursor = this.getContentResolver().query(
					MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
					null, MediaStore.Audio.AudioColumns.TITLE);

			MymusicList = new ArrayList<MusicInfo>();

			int musicCount = 0;

			for (int i = 0; i < mAudioCursor.getCount(); i++) {
				mAudioCursor.moveToNext();

				int duration = mAudioCursor
						.getInt(mAudioCursor
								.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				// 筛选时长大于2分钟的歌曲
				if (duration > 180 * 1000) {
					musicCount++;
					// 歌曲ID
					int musicId = mAudioCursor.getInt(mAudioCursor
							.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
					// 歌曲路径(URI)
					String musicPath = mAudioCursor
							.getString(mAudioCursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
					// 歌曲名
					String musicTitle = mAudioCursor
							.getString(mAudioCursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
					// 歌手
					String musicSinger = mAudioCursor
							.getString(mAudioCursor
									.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
					// 向容器添加以供ListView使用
					MymusicList.add(new MusicInfo(musicId, musicPath,
							musicTitle, musicSinger, duration));
				}
			}
			mAudioCursor.close();

			System.out.println("加载本地音乐：" + musicCount);
			loadFlag++;
		}
	}

	private void initView2() {
		linearLayouts = new LinearLayout[3];

		linearLayouts[0] = (LinearLayout) findViewById(R.id.lay1);

		linearLayouts[1] = (LinearLayout) findViewById(R.id.lay2);

		linearLayouts[2] = (LinearLayout) findViewById(R.id.lay3);

		linearLayouts[1].setBackgroundResource(R.drawable.base_edit_input);

		textViews = new TextView[3];
		textViews[0] = (TextView) findViewById(R.id.fratext1);
		textViews[1] = (TextView) findViewById(R.id.fratext2);
		textViews[2] = (TextView) findViewById(R.id.fratext3);
		textViews[1].setTextColor(getResources()
				.getColor(R.color.lightseagreen));
		linearLayouts[1].setSelected(true);
	}

	/*
	 * private void initView() { mTabs = new Button[3]; mTabs[0] = (Button)
	 * findViewById(R.id.btn_message); mTabs[1] = (Button)
	 * findViewById(R.id.btn_contract); mTabs[2] = (Button)
	 * findViewById(R.id.btn_set); list_tips = (ImageView)
	 * findViewById(R.id.iv_recent_tips); playing_tips = (ImageView)
	 * findViewById(R.id.iv_contact_tips); // 把第一个tab设为选中状态
	 * mTabs[0].setSelected(true); }
	 */

	private void initTab() {
		musicListFragment = new MusicListFragment();
		musicPlayingFragment = new MusicPlayingFragment();
		setFrament = new SetFrament();
		/*
		 * fragments = new Fragment[3];
		 * 
		 * fragments[0] = mFragmentMan.findFragmentById(
		 * R.id.fragment_musiclist); fragments[1] =
		 * mFragmentMan.findFragmentById( R.id.fragment_musicplaying);
		 * fragments[2] = mFragmentMan.findFragmentById( R.id.fragment_set);
		 * mFragmentMan.beginTransaction().hide(fragments[0])
		 * .hide(fragments[1]).hide(fragments[2]).show(fragments[0]) .commit();
		 */
		fragments = new Fragment[] { musicListFragment, musicPlayingFragment,
				setFrament };
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_mycontainer, fragments[1])
				.add(R.id.fragment_mycontainer, fragments[0])
				.hide(musicListFragment)
				.show(musicPlayingFragment).commit();
	}

	/*public void switchContent(Fragment from, Fragment to) {
		if (fragments[index] != to) {
			//fragments[index] = to;
			FragmentTransaction transaction = mFragmentMan.beginTransaction()
					.setCustomAnimations(android.R.anim.fade_in,
							android.R.anim.fade_out);
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(from).add(R.id.fragment_mycontainer, to).show(to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
	}*/

	/**
	 * button点击事件
	 * 
	 * @param view
	 */
	public void LayoutOnTabSelect(View view) {

		resetlaybg();
		switch (view.getId()) {
		case R.id.lay1:
			index = 0;	
			break;
		case R.id.lay2:
			index = 1;
			break;
		case R.id.lay3:
			index = 2;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_mycontainer, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		linearLayouts[index].setBackgroundResource(R.drawable.base_edit_input);
		textViews[index].setTextColor(getResources().getColor(
				R.color.lightseagreen));
		currentTabIndex = index;
	}

	public void resetlaybg() {
		for (int i = 0; i < 3; i++) {
			linearLayouts[i].setBackgroundResource(R.drawable.top_bar);
			textViews[i].setTextColor(getResources().getColor(
					R.color.base_color_text_white));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		if (first) {
			editor.putBoolean(FIRST_RUN, false);
		}
		editor.commit();
	}

	@Override
	public void onMusicSelected(int position) {
		musicPlayingFragment = (MusicPlayingFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_container);
		if (musicPlayingFragment != null) {
			musicPlayingFragment.updateView(position);
		} else {
			musicPlayingFragment=new MusicPlayingFragment();
			musicPlayingFragment.updateView(position);
		}
	}

	@Override
	public void onMusicSelected(int position, MusicInfo musicInfo) {
		musicPlayingFragment = (MusicPlayingFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_container);
		if (musicPlayingFragment != null) {
			musicPlayingFragment.updateView(position);
		} else {
			System.out.println("另一个碎片为空");
		}
	}

	/*
	 * @Override public void passMusicList(List<MusicInfo> musicList) {
	 * System.out.println(musicList.size() + ""); musicPlayingFragment =
	 * (MusicPlayingFragment) getSupportFragmentManager()
	 * .findFragmentById(R.id.fragment_container); if (musicPlayingFragment !=
	 * null) { musicPlayingFragment.transList(musicList); } else {
	 * System.out.println("另一个碎片为空"); }
	 * 
	 * }
	 */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub

		/*
		 * resetlaybg(); if (index == 0) { if (arg1.getX() > arg0.getX() +
		 * DISTANT) { getSupportFragmentManager().beginTransaction()
		 * .hide(fragments[0]).hide(fragments[1])
		 * .hide(fragments[2]).show(fragments[1]).commit(); linearLayouts[1]
		 * .setBackgroundResource(R.drawable.base_edit_input);
		 * textViews[1].setTextColor(getResources().getColor(
		 * R.color.lightseagreen)); index = 1; } else { linearLayouts[0]
		 * .setBackgroundResource(R.drawable.base_edit_input);
		 * textViews[0].setTextColor(getResources().getColor(
		 * R.color.lightseagreen)); }
		 * 
		 * } // 当是Fragment1的时候 else if (index == 1) { if (arg1.getX() >
		 * arg0.getX() + DISTANT) {
		 * getSupportFragmentManager().beginTransaction()
		 * .hide(fragments[0]).hide(fragments[1])
		 * .hide(fragments[2]).show(fragments[2]).commit(); linearLayouts[2]
		 * .setBackgroundResource(R.drawable.base_edit_input);
		 * textViews[2].setTextColor(getResources().getColor(
		 * R.color.lightseagreen)); index = 2; } else if (arg0.getX() >
		 * arg1.getX() + DISTANT) {
		 * getSupportFragmentManager().beginTransaction()
		 * .hide(fragments[0]).hide(fragments[1])
		 * .hide(fragments[2]).show(fragments[0]).commit(); linearLayouts[0]
		 * .setBackgroundResource(R.drawable.base_edit_input);
		 * textViews[0].setTextColor(getResources().getColor(
		 * R.color.lightseagreen)); index = 0; } else { linearLayouts[1]
		 * .setBackgroundResource(R.drawable.base_edit_input);
		 * textViews[1].setTextColor(getResources().getColor(
		 * R.color.lightseagreen)); } } // 当是Fragment2的时候 else if (index == 2) {
		 * if (arg0.getX() > arg1.getX() + DISTANT) {
		 * getSupportFragmentManager().beginTransaction()
		 * .hide(fragments[0]).hide(fragments[1])
		 * .hide(fragments[2]).show(fragments[1]).commit(); linearLayouts[1]
		 * .setBackgroundResource(R.drawable.base_edit_input);
		 * textViews[1].setTextColor(getResources().getColor(
		 * R.color.lightseagreen)); index = 1; } else { linearLayouts[2]
		 * .setBackgroundResource(R.drawable.base_edit_input);
		 * textViews[2].setTextColor(getResources().getColor(
		 * R.color.lightseagreen)); } }
		 */

		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		resetlaybg();

		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static long firstTime;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
			
			
		} else {
			Toast.makeText(getApplication(), "再按一次退出程序", Toast.LENGTH_LONG).show();;
		}
		firstTime = System.currentTimeMillis();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}

    
	

}
