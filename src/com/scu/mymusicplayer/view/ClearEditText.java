package com.scu.mymusicplayer.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.ui.NetSearchActivity;
import com.scu.mymusicplayer.ui.TestActivity;
import com.scu.mymusicplayer.ui.TestActivity2;

/**
 * ��������ܵ��ı������
 * 
 * @ClassName: ClearEditText
 * @Description: TODO
 * @author smile
 * @date 2014-5-4 ����4:18:45
 */
public class ClearEditText extends EditText implements OnFocusChangeListener,
		TextWatcher {
	/**
	 * ɾ����ť������
	 */
	private Drawable mClearDrawable;
	private Intent intent;
	private String content="";
	private Drawable mSearchDrawable;

	public ClearEditText(Context context) {
		this(context, null);
	}

	public ClearEditText(Context context, AttributeSet attrs) {
		// ���ﹹ�췽��Ҳ����Ҫ����������ܶ����Բ�����XML���涨��
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mSearchDrawable =getCompoundDrawables()[0];
		if(mSearchDrawable==null){
		mSearchDrawable = getResources().getDrawable(R.drawable.icon_msg_search2);
		}
		mSearchDrawable.setBounds(0, 0, mSearchDrawable.getIntrinsicWidth(),
				mSearchDrawable.getIntrinsicHeight());
		
		mClearDrawable = getCompoundDrawables()[2];
		if (mClearDrawable == null) {
			mClearDrawable = getResources()
					.getDrawable(R.drawable.search_clear);
		}
		mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
				mClearDrawable.getIntrinsicHeight());
		setClearIconVisible(false);
		setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	/**
	 * ��Ϊ���ǲ���ֱ�Ӹ�EditText���õ���¼������������ü�ס���ǰ��µ�λ����ģ�����¼� �����ǰ��µ�λ�� �� EditText�Ŀ�� -
	 * ͼ�굽�ؼ��ұߵļ�� - ͼ��Ŀ�� �� EditText�Ŀ�� - ͼ�굽�ؼ��ұߵļ��֮�����Ǿ�������ͼ�꣬��ֱ����û�п���
	 */
	@Override 
   public boolean onTouchEvent(MotionEvent event) { 
       if (getCompoundDrawables()[2] != null) { 
           if (event.getAction() == MotionEvent.ACTION_UP) { 
           	boolean touchable = event.getX() > (getWidth() 
                       - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) 
                       && (event.getX() < ((getWidth() - getPaddingRight())));
               if (touchable) { 
                   this.setText(""); 
               } 
           } 
       }
       if(getCompoundDrawables()[0]!= null){
    	   if (event.getAction() == MotionEvent.ACTION_UP) { 
    	   boolean touchable = event.getX()>0
    			   &&(event.getX()<(getPaddingLeft()+getCompoundDrawables()[0].getIntrinsicWidth()+getCompoundPaddingLeft()));
    	   if(touchable){
    		   Bundle bundle=new Bundle();
    		   bundle.putString("SearchKey", content);
    		   
    		   intent=new Intent(getContext(), NetSearchActivity.class);
    		   intent.putExtras(bundle);
    		   this.getContext().startActivity(intent);
    	   }
       }
       }

       return super.onTouchEvent(event); 
   }
	/**
	 * ��ClearEditText���㷢���仯��ʱ���ж������ַ��������������ͼ�����ʾ������
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			setClearIconVisible(getText().length() > 0);
		} else {
			setClearIconVisible(false);
		}
	}

	/**
	 * �������ͼ�����ʾ�����أ�����setCompoundDrawablesΪEditText������ȥ
	 * 
	 * @param visible
	 */
	protected void setClearIconVisible(boolean visible) {
		
		
		Drawable left = visible ? mSearchDrawable : null;
		Drawable right = visible ? mClearDrawable : null;
		setCompoundDrawables(left,
				getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}
	protected void setSearchIconChange(boolean change){
		
	}
	/**
	 * ��������������ݷ����仯��ʱ��ص��ķ���
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		setClearIconVisible(s.length() > 0);
		//setSearchIconChange(s.length()>0);
		content=s.toString();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	/**
	 * ���ûζ�����
	 */
	public void setShakeAnimation() {
		this.setAnimation(shakeAnimation(5));
	}

	/**
	 * �ζ�����
	 * 
	 * @param counts
	 *            1���ӻζ�������
	 * @return
	 */
	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}
}
