package com.scu.mymusicplayer.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.adpter.NetSongListAdapter;
import com.scu.mymusicplayer.application.Options;
import com.scu.mymusicplayer.bean.SongInfo;
import com.scu.mymusicplayer.config.Config;
import com.scu.mymusicplayer.util.JosnPaserEngine;
import com.scu.mymusicplayer.view.HeaderLayout;
import com.scu.mymusicplayer.view.HeaderLayout.HeaderStyle;
import com.scu.mymusicplayer.view.HeaderLayout.onLeftImageButtonClickListener;
import com.scu.mymusicplayer.view.ListBounceView;
import com.show.api.Constants;
import com.show.api.ShowApiRequest;

public class NetMusicInfoActivity extends Activity{
	
	private ProgressDialog dialog;
	public HeaderLayout mHeaderLayout;
	Map<String, String> params = new HashMap<String, String>();

	private List<SongInfo> songList=new ArrayList<SongInfo>();
	private ImageView img_paihang;
	private ListBounceView MysongListview;
	private String img_url;
	DisplayImageOptions option = Options.getListOptions();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_paihang);
		
		initHeadLayout();
		initBodyLayout();
		setDialog();
		getMyBundle();
		new MyPaiHangTask().execute(params);
		
	}
	private void setDialog() {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在加载...");
	}
	private void getMyBundle() {
		// TODO Auto-generated method stub
		Bundle myBundle = this.getIntent().getExtras();
		String itemid = myBundle.getString("itemid");
		System.out.println(itemid);
		params.put("itemid", itemid);
		
	}
	private void initBodyLayout() {
		// TODO Auto-generated method stub
		img_paihang=(ImageView) findViewById(R.id.img_headline);
		MysongListview=(ListBounceView) findViewById(R.id.paihang_List);
		
	}
	private void initHeadLayout() {

		mHeaderLayout = (HeaderLayout) findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_LIFT_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton("音乐排行",
				R.drawable.base_action_bar_back_bg_selector,
				new OnActivityLeftButtonClickListener());
		
	}
	
	public class OnActivityLeftButtonClickListener implements
	onLeftImageButtonClickListener {

		@Override
		public void onClick() {
			Toast.makeText(getApplicationContext(), "点击了", Toast.LENGTH_SHORT).show();
			finish();
		}
}
	class MyPaiHangTask extends 
	AsyncTask<Map<String, String>, Void, List<SongInfo>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected List<SongInfo> doInBackground(Map<String, String>... args) {

			String itemid = args[0].get("itemid");
			SimpleDateFormat df = new SimpleDateFormat(
					Constants.DATE_TIME_FORMAT);
			String res = new ShowApiRequest("http://route.showapi.com/213-3",
					Config.MYAPP_ID, Config.MYAPP_SERCRETKEY)
					.addTextPara("showapi_timestamp", df.format(new Date()))
					.addTextPara("itemid", itemid).post();

			JosnPaserEngine.getPaiHang(res, songList);
			img_url=JosnPaserEngine.getUrl();
			
			return songList;
		}

		@Override
		protected void onPostExecute(List<SongInfo> result) {

			super.onPostExecute(result);
			System.out.println(img_url);
			imageLoader.displayImage(img_url, img_paihang, option);
			MysongListview.setAdapter(new NetSongListAdapter(
					getApplicationContext(), result));
			// tvSearchKey.setText(result);
			
			dialog.dismiss();
		}

	}
}
