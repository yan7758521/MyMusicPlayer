package com.scu.mymusicplayer.fragment;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.application.MyApplication;
import com.scu.mymusicplayer.bean.MusicInfo;
import com.scu.mymusicplayer.view.HeaderLayout;
import com.scu.mymusicplayer.view.HeaderLayout.HeaderStyle;
import com.scu.mymusicplayer.view.HeaderLayout.onLeftImageButtonClickListener;
import com.scu.mymusicplayer.view.HeaderLayout.onRightImageButtonClickListener;

public class BaseFragment extends Fragment {
	public HeaderLayout mHeaderLayout;

	protected View contentView;
	
	public LayoutInflater mInflater;
	public MyApplication myApplication;
	public OnHeadlineSelectedListener mCallback;
	
	private Handler handler = new Handler();
	
	public void runOnWorkThread(Runnable action) {
		new Thread(action).start();
	}

	public void runOnUiThread(Runnable action) {
		handler.post(action);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		myApplication=MyApplication.getInstance();
		mInflater = LayoutInflater.from(getActivity());
	}

	public BaseFragment() {
		
	}
	
	Toast mToast;


	public void ShowToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public void ShowToast(int text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}
	
	
	public View findViewById(int paramInt) {
		return getView().findViewById(paramInt);
	}
	
	
	/**
	 * ֻ��title initTopBarLayoutByTitle
	 * @Title: initTopBarLayoutByTitle
	 * @throws
	 */
	
	/**
	 * ��ʼ��������-�����Ұ�ť
	 * 
	 * @return void
	 * @throws
	 */
	public void initTopBarForBoth(String titleName, int rightDrawableId,
			onRightImageButtonClickListener listener) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName,
				R.drawable.base_action_bar_back_bg_selector,
				new OnLeftButtonClickListener());
		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,
				listener);
	}
	public void initTopBarForOnlyTitle(String titleName) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle(titleName);
	}


	/**
	 * ֻ����߰�ť��Title initTopBarLayout
	 * 
	 * @throws
	 */
	public void initTopBarForLeft(String titleName) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_LIFT_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName,
				R.drawable.base_action_bar_back_bg_selector,
				new OnLeftButtonClickListener());
	}
	
	/** �ұ�+title
	  * initTopBarForRight
	  * @return void
	  * @throws
	  */
	public void initTopBarForRight(String titleName,int rightDrawableId,
			onRightImageButtonClickListener listener) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_RIGHT_IMAGEBUTTON);
		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,
				listener);
	}
	
	// ��߰�ť�ĵ���¼�
	public class OnLeftButtonClickListener implements
			onLeftImageButtonClickListener {

		@Override
		public void onClick() {
			ShowToast("������أ���");
		}
	}
	
	/**
	 * ��������ҳ�� startAnimActivity
	 * @throws
	 */
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}
	
	public void startAnimActivity(Class<?> cla) {
		getActivity().startActivity(new Intent(getActivity(), cla));
	}
	
	
	public void updateView(int position){
		
	}
	
	
	// �������fragment��Activtiy����ʵ������ӿ�
    public interface OnHeadlineSelectedListener {
        public void onMusicSelected(int position);
        public void onMusicSelected(int position,MusicInfo musicInfo);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // ����Ϊ�˱�֤Activity����ʵ�������Իص��Ľӿڡ����û�У������׳�һ���쳣��
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

}

