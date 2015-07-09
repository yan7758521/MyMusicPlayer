package com.scu.mymusicplayer.fragment;

import java.text.SimpleDateFormat;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.bean.Db_music;
import com.scu.mymusicplayer.bean.MusicInfo;
import com.scu.mymusicplayer.util.LyricGesture;
import com.scu.mymusicplayer.view.HeaderLayout.onRightImageButtonClickListener;
import com.scu.mymusicplayer.view.LyricView;

public class MusicPlayingFragment extends BaseFragment {
	String text = "正在播放";
	private static int currentPosition = 1;
	private ImageButton img_bnt_back;
	private static TextView tv_musicName;
	private static TextView tv_singerName;
	private ImageView image_face;
	private ImageButton img_bnt_leftSong;
	private ImageButton img_bnt_playing;
	private ImageButton img_bnt_nextSong;
	private static SeekBar seekBar_play;
	private static TextView tv_nowtime;
	private static TextView tv_alltime;
	private onRightImageButtonClickListener listenner;
	public static MediaPlayer mediaPlayer = null;
	private static Handler mScheduleHandler = new Handler();

	private static Handler yScheduleHandler = new Handler();
	private static SimpleDateFormat nowTimeFormat = null;
	private static MusicInfo nowMusic = null;
	private static Db_music db_music = new Db_music();

	private static LyricView lyricView;

	private static boolean running = true;
	private float ldriftx;
	private float ldrifty;
	
	private String lrc_path;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		nowTimeFormat = new SimpleDateFormat("mm:ss");
		nowMusic = db_music.findByMusicId(currentPosition);
		initHeadLayout();

		initBodyView();
		// initThread();
		initSeekBar();

		// doSeekBarUpdate();
	}

	private void initHeadLayout() {
		initTopBarForBoth(text, R.drawable.base_action_bar_add_bg_selector,
				new onRightImageButtonClickListener() {

					@Override
					public void onClick() {
						ShowToast("你点击了！");

					}
				});

	}

	public void initBodyView() {
		img_bnt_back = (ImageButton) findViewById(R.id.name_back);
		img_bnt_leftSong = (ImageButton) findViewById(R.id.left_song);
		img_bnt_playing = (ImageButton) findViewById(R.id.play);
		img_bnt_nextSong = (ImageButton) findViewById(R.id.right_song);
		tv_musicName = (TextView) findViewById(R.id.musicName);
		tv_singerName = (TextView) findViewById(R.id.singer_name);
		tv_musicName.setText(nowMusic.getM_musicTitle());
		tv_singerName.setText(nowMusic.getM_musicSinger());
		img_bnt_back.setOnClickListener(new backToListListener());
		img_bnt_leftSong.setOnClickListener(new leftSongListener());
		img_bnt_playing.setOnClickListener(new playListener());
		img_bnt_nextSong.setOnClickListener(new nextSongListener());

	}

	public void slidestart() {

		lyricView.showprogress = true;

	}

	public boolean updatelab(float dx, float dy, boolean toggle) {
		if (toggle) {
			ldriftx = dx + ldriftx;
			ldrifty = dy + ldrifty;
		} else {
			if (Math.abs(ldriftx) < Math.abs(ldrifty)) {
				return true;
			}
			ldriftx = 0;
			ldrifty = 0;
		}
		return false;
	}

	public void updateprogress(float dx, float dy) {
		lyricView.driftx = dx + lyricView.driftx;
		lyricView.drifty = dy + lyricView.drifty;
		lyricView.invalidate();// 更新视图
	}

	public void updateplayer() {
		lyricView.showprogress = false;
		lyricView.index = lyricView.index + lyricView.temp;
		lyricView.driftx = 0;
		lyricView.drifty = 0;
		if (lyricView.repair()) {
			mediaPlayer.seekTo((lyricView.times.get(lyricView.index - 1))
					.intValue());
		} else
			mediaPlayer.seekTo(0);

	}

	@Override
	public void updateView(int position) {
		super.updateView(position);
		if (currentPosition == position) {
			// img_bnt_playing.performClick();

		} else {
			nowMusic = db_music.findByMusicId(position);

			System.out.println(nowMusic.getM_musicPath());
			
			lrc_path=nowMusic.getM_musicPath()
					.replace(".mp3", ".lrc");

			System.out.println(lrc_path);
			playMusic(nowMusic);
			currentPosition = position;

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_playing, null);

		return view;
	}

	public void playMusic(MusicInfo musicInfo) {

		// 初始播放时间清零
		tv_nowtime.setText("00:00");

		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(musicInfo.getM_musicPath());
			mediaPlayer.prepare();
			mediaPlayer.start();

			lyricView.setPath(lrc_path);

			lyricView.songAlltime = nowMusic.getM_duration();
			lyricView.changeLrc = true;

			/*
			 * img_bnt_playing.setImageDrawable(getResources().getDrawable(
			 * R.drawable.stop));
			 */

			// 更新进度条
			doSeekBarUpdate();

			// 更新播放总时间
			int musicDurationTime = mediaPlayer.getDuration();
			SimpleDateFormat totalTimeFormat = new SimpleDateFormat("mm:ss");
			String ms2 = totalTimeFormat.format(musicDurationTime);
			tv_alltime.setText(ms2);

			tv_musicName.setText(musicInfo.getM_musicTitle());
			tv_singerName.setText(musicInfo.getM_musicSinger());

			mediaPlayer
					.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {

							playMusic(nowMusic);

							// 更新歌曲名和歌者
							tv_musicName.setText(nowMusic.getM_musicTitle());
							tv_singerName.setText(nowMusic.getM_musicSinger());

							// 更新播放总时间
							int musicDurationTime = mediaPlayer.getDuration();
							SimpleDateFormat totalTimeFormat = new SimpleDateFormat(
									"mm:ss");
							String ms2 = totalTimeFormat
									.format(musicDurationTime);
							tv_alltime.setText(ms2);
						}
					});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void initSeekBar() {
		SimpleDateFormat totalTimeFormat = new SimpleDateFormat("mm:ss");
		String ms2 = totalTimeFormat.format(nowMusic.getM_duration());
		seekBar_play = (SeekBar) findViewById(R.id.playback_seeker);
		tv_nowtime = (TextView) findViewById(R.id.now_time);
		tv_alltime = (TextView) findViewById(R.id.all_time);
		tv_alltime.setText(ms2);
		seekBar_play.setProgress(0);

		lyricView = (LyricView) getActivity().findViewById(R.id.audio_lrc);

		lyricView.setPath("/sdcard/下雨天.lrc");

		lyricView.setLongClickable(true);
		lyricView.setOnTouchListener(new LyricGesture(this));

		seekBar_play.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				mediaPlayer.start();
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				mediaPlayer.pause();
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				// 用户手动拖动进度条则更新播放进度
				if (arg2)
					mediaPlayer.seekTo(seekBar_play.getProgress());
			}
		});
	}

	private static void doSeekBarUpdate() {
		// 设置进度条的最大值为播放时间
		seekBar_play.setMax(mediaPlayer.getDuration());

		// 新开一个线程进行UI更新
		mScheduleHandler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				if (mediaPlayer.isPlaying() && !seekBar_play.isPressed()) {
					// 更新播放进度
					int nowTime = mediaPlayer.getCurrentPosition();
					seekBar_play.setProgress(nowTime);

					System.out.println(nowTime + "");

					System.out.println(lyricView.index + "");

					// lyricView.changeLrc=false;
					lyricView.updateindex(nowTime);

					lyricView.invalidate();
					// 更新播放时间
					String nowTime_s = nowTimeFormat.format(nowTime);
					tv_nowtime.setText(nowTime_s);
					// doLyricUpdate();
					try {
						mScheduleHandler.postDelayed(this, 1000);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		});
	}

	class backToListListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			ShowToast("点击了返回键！");

		}
	}

	class leftSongListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			--currentPosition;

			if (currentPosition == 0)
				currentPosition = 1;
			nowMusic = db_music.findByMusicId(currentPosition);
			ShowToast("正在播放：" + nowMusic.getM_musicTitle());

			playMusic(nowMusic);

			/*
			 * lyricView.setPath("/sdcard/天后sad.lrc");
			 * 
			 * lyricView.songAlltime = nowMusic.getM_duration();
			 * lyricView.changeLrc = true;
			 */

		}

	}

	class nextSongListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			++currentPosition;
			nowMusic = db_music.findByMusicId(currentPosition);
			ShowToast("下一首" + nowMusic.getM_musicTitle());

			playMusic(nowMusic);

			/*
			 * lyricView.setPath("/sdcard/天后sad.lrc");
			 * 
			 * lyricView.songAlltime = nowMusic.getM_duration();
			 * lyricView.changeLrc = true;
			 */

		}

	}

	class playListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				ShowToast("暂停播放:" + nowMusic.getM_musicTitle());
				img_bnt_playing.setImageDrawable(getResources().getDrawable(
						R.drawable.play));
			} else {
				img_bnt_playing.setImageDrawable(getResources().getDrawable(
						R.drawable.stop));
				ShowToast("开始播放:" + nowMusic.getM_musicTitle());

				doSeekBarUpdate();

				if (tv_nowtime.getText().equals("00:00")) {
					playMusic(nowMusic);
				} else {
					mediaPlayer.start();
				}
			}

		}

	}

}