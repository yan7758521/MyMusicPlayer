package com.scu.mymusicplayer.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.adpter.NetSongListAdapter;
import com.scu.mymusicplayer.bean.SongInfo;
import com.scu.mymusicplayer.config.Config;
import com.scu.mymusicplayer.util.JosnPaserEngine;
import com.scu.mymusicplayer.view.HeaderLayout;
import com.scu.mymusicplayer.view.HeaderLayout.HeaderStyle;
import com.scu.mymusicplayer.view.HeaderLayout.onLeftImageButtonClickListener;
import com.scu.mymusicplayer.view.ListBounceView;
import com.show.api.Constants;
import com.show.api.ShowApiRequest;

public class NetSearchActivity extends Activity {
	// private TextView tvSearchKey;
	private ProgressDialog dialog;
	public HeaderLayout mHeaderLayout;
	Map<String, String> params = new HashMap<String, String>();

	private List<SongInfo> songList;

	private TextView tv_result;
	private ListBounceView MysongListview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		setContentView(R.layout.activity_musiclist_net);
		initHeadLayout();
		initBodyView();
		setDialog();
		getMyBundle();
		new MyTask().execute(params);

	}

	private void initHeadLayout() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_LIFT_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton("网络列表",
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

	private void getMyBundle() {

		Bundle myBundle = this.getIntent().getExtras();
		String name = myBundle.getString("SearchKey");
		System.out.println(name);
		params.put("keyword", name);
	}

	private void setDialog() {
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在加载...");

	}

	private void initBodyView() {
		// tvSearchKey=(TextView) findViewById(R.id.tv_searchKey);
		tv_result = (TextView) findViewById(R.id.searchResult);
		MysongListview = (ListBounceView) findViewById(R.id.NetSong_List);

	}

	public class MyTask extends
			AsyncTask<Map<String, String>, Void, List<SongInfo>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected List<SongInfo> doInBackground(Map<String, String>... args) {

			String keyWord = args[0].get("keyword");
			SimpleDateFormat df = new SimpleDateFormat(
					Constants.DATE_TIME_FORMAT);
			String res = new ShowApiRequest("http://route.showapi.com/213-1",
					Config.MYAPP_ID, Config.MYAPP_SERCRETKEY)
					.addTextPara("showapi_timestamp", df.format(new Date()))
					.addTextPara("keyword", keyWord).post();

			songList = JosnPaserEngine.getSongs(res);
			return songList;
		}

		@Override
		protected void onPostExecute(List<SongInfo> result) {

			super.onPostExecute(result);
			tv_result.setText("搜索结果：" + result.size() + "首");
			MysongListview.setAdapter(new NetSongListAdapter(
					getApplicationContext(), result));
			// tvSearchKey.setText(result);
			for (int i = 0; i < songList.size(); i++) {
				System.out.println(songList.get(i).toString());
			}
			dialog.dismiss();
		}

	}

}
