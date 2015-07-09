package com.scu.mymusicplayer.fragment;

import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.adpter.ListAdapter;
import com.scu.mymusicplayer.bean.Db_music;
import com.scu.mymusicplayer.bean.MusicInfo;
import com.scu.mymusicplayer.view.HeaderLayout.onRightImageButtonClickListener;
import com.scu.mymusicplayer.view.ListBounceView;
import com.scu.mymusicplayer.view.MyLetterView;
import com.scu.mymusicplayer.view.MyLetterView.OnTouchingLetterChangedListener;

public class MusicListFragment extends BaseFragment implements OnGestureListener {
	private MyLetterView letterView;
	private TextView dialog;
	private static Db_music db_music;
	String text = "�����б�";
	private ListView listView = null;
	private ListAdapter adapter = null;
	private static List<MusicInfo> musicList = null;
	// ��ǰ����λ��
	public static int curPosition = 0;
	// ��ֻ֤����һ��sd��
	private static int loadFlag = 1;
	private FragmentManager fragmentManager;
	private FragmentTransaction ft;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_musiclist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		db_music=new Db_music();
		fragmentManager=getFragmentManager();
		initHeadLayout();
		initListView();
		
	}
	
	private void initHeadLayout() {
		initTopBarForBoth(text, R.drawable.base_action_bar_add_bg_selector,
				new onRightImageButtonClickListener() {

					@Override
					public void onClick() {
						ShowToast("�����ˣ�");

					}
				});
		
	}
	public void initListView() {
		letterView = (MyLetterView) findViewById(R.id.right_letter);
		dialog = (TextView) findViewById(R.id.dialog);
		letterView.setTextView(dialog);
		letterView
				.setOnTouchingLetterChangedListener(new LetterListViewListener());

		listView = (ListBounceView) findViewById(R.id.MusicListView);
		musicList = getMusicInfoList();
		adapter=new ListAdapter(getActivity(), musicList);
		listView.setAdapter(adapter);
		
		
		
		listView.setItemsCanFocus(false);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast(adapter.getItem(position).toString());
				mCallback.onMusicSelected(adapter.getItem(position).getId());
				
			}
		});
	}

	public List<MusicInfo> getMusicInfoList() {
		try {
			
			musicList=db_music.getAllMusic();
			System.out.println("jiazaile");
			
		} catch (Exception e) {
			ShowToast("���س�������");
		}
		return musicList;
	}
	
	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(String s) {
			// ����ĸ�״γ��ֵ�λ��
			int position = adapter.getPositionForSection(s.charAt(0));
			if (position != -1) {
				listView.setSelection(position);
			}
		
		}
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
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

	

}
