package com.scu.mymusicplayer.view;

import android.widget.ListView;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class ListBounceView extends ListView {
	private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
	
	private Context mContext;
	private int mMaxYOverscrollDistance;
	
	public ListBounceView(Context context){
		super(context);
		mContext = context;
		initListBounceView();
	}
	
	public ListBounceView(Context context, AttributeSet attrs){
		super(context, attrs);
		mContext = context;
		initListBounceView();
	}
	
	private void initListBounceView(){
		final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;
      
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
	}
	
	//实现弹性下拉的函数
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, 
			                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, 
				scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
	}
}
