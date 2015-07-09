package com.scu.mymusicplayer.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.application.Options;
import com.scu.mymusicplayer.ui.NetMusicInfoActivity;
import com.scu.mymusicplayer.ui.NetSearchActivity;
import com.scu.mymusicplayer.ui.TestActivity;
import com.scu.mymusicplayer.view.ClearEditText;
import com.scu.mymusicplayer.view.HeaderLayout.onRightImageButtonClickListener;
import com.scu.mymusicplayer.view.MyImageView;
import com.scu.mymusicplayer.view.MyImageView.OnViewClick;

public class SetFrament extends BaseFragment {
	
	DisplayImageOptions option =Options.getListOptions();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	String text = "网络音乐";
	private ClearEditText clearEditText;
	private MyImageView imageView1;
	private MyImageView imageView2;
	private MyImageView imageView3;
	private MyImageView imageView4;
	private MyImageView imageView5;
	private MyImageView imageView6;
	private List<MyImageView> list=new ArrayList<MyImageView>(); 
	public static String urls[]={
	"http://h.hiphotos.baidu.com/image/pic/item/d31b0ef41bd5ad6e1a065ce783cb39dbb6fd3c3b.jpg",
	"http://h.hiphotos.baidu.com/image/pic/item/f31fbe096b63f624249ffa0e8544ebf81b4ca3f9.jpg",
	"http://f.hiphotos.baidu.com/image/pic/item/d6ca7bcb0a46f21fa3504e4cf4246b600c33ae4c.jpg",
	"http://h.hiphotos.baidu.com/image/pic/item/18d8bc3eb13533fa25fda038aad3fd1f41345b58.jpg",
	"http://e.hiphotos.baidu.com/image/pic/item/b812c8fcc3cec3fdc8d40778d488d43f869427d0.jpg",
	"http://b.hiphotos.baidu.com/image/pic/item/f7246b600c3387441a264d75530fd9f9d62aa0dc.jpg"
	};
	private Intent intent;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initHeadLayout();
		clearEditText=(ClearEditText) findViewById(R.id.et_msg_search);
		initBodyView();
		//initGetView();
		
	}

	private void initGetView() {
		list.add(imageView1);
		list.add(imageView2);
		list.add(imageView3);
		list.add(imageView4);
		list.add(imageView5);
		list.add(imageView6);
		for(int i=0;i<6;i++)
		imageLoader.displayImage(urls[i], list.get(i), option);
		
	}

	private void initBodyView() {
		imageView1=(MyImageView) findViewById(R.id.contentMusicAll);
		imageView2=(MyImageView) findViewById(R.id.contentMusic1);
		imageView3=(MyImageView) findViewById(R.id.contentMusic2);
		imageView4=(MyImageView) findViewById(R.id.contentMusic3);
		imageView5=(MyImageView) findViewById(R.id.contentMusic4);
		imageView6=(MyImageView) findViewById(R.id.contentMusic5);
		imageView1.setOnClickIntent(new OnViewClick() {
			
			
			
			@Override
			public void onClick() {
				//ShowToast("点击了1");
				Bundle bundle=new Bundle();
	    		bundle.putString("itemid", "4");
	    		intent=new Intent();
	    		intent.putExtras(bundle);
				intent.setClass(getActivity(), NetMusicInfoActivity.class);


	    		getActivity().startActivity(intent);
				
			}
		});
		imageView2.setOnClickIntent(new OnViewClick() {
			
			@Override
			public void onClick() {
				ShowToast("点击了2");
				Bundle bundle=new Bundle();
	    		bundle.putString("itemid", "101");
	    		intent=new Intent();
	    		intent.putExtras(bundle);
				intent.setClass(getActivity(), NetMusicInfoActivity.class);


	    		getActivity().startActivity(intent);
				
				
			}
		});
		imageView3.setOnClickIntent(new OnViewClick() {
	
			@Override
			public void onClick() {
				ShowToast("点击了3");
		
	}
});
		imageView4.setOnClickIntent(new OnViewClick() {
			
			@Override
			public void onClick() {
				ShowToast("点击了4");
				
			}
		});

		imageView5.setOnClickIntent(new OnViewClick() {
			
			@Override
			public void onClick() {
				ShowToast("点击了5");
				
			}
		});

		imageView6.setOnClickIntent(new OnViewClick() {
			
			@Override
			public void onClick() {
				ShowToast("点击了6");
				
			}
		});
	}
	
	private void initHeadLayout() {
		initTopBarForBoth(text, R.drawable.base_action_bar_add_bg_selector,
				new onRightImageButtonClickListener() {

					@Override
					public void onClick() {
						ShowToast("你点击了！");
						clearEditText.setShakeAnimation();

					}
				});
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_setting, null);
		ViewUtils.inject(this, view);

		return view;
	}
	
	
	@ViewInject(R.id.tv_gedan)
	private TextView tvGedan;
	
	@ViewInject(R.id.tv_paihang)
	private TextView tvPaihang;
	
	@ViewInject(R.id.tv_singer)
	private TextView tvSinger;
	
	@ViewInject(R.id.tv_tuijian)
	private TextView tvTuijian;
	
	
	

}
