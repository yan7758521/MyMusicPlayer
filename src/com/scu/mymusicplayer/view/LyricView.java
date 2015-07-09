package com.scu.mymusicplayer.view;

import java.util.ArrayList;
import java.util.List;

import com.scu.mymusicplayer.bean.LrcInfo;
import com.scu.mymusicplayer.util.LrcParser;
import com.scu.mymusicplayer.util.TimeParseTool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class LyricView extends TextView {
	private Paint mPaint;
	private float mX;
	private Paint mPathPaint;
	public int index = 0;
	public float mTouchHistoryY;
	private int mY;
	private long currentDuringTime;// 当前歌词持续时间
	private float middleY;// Y轴中间
	private final int DY = 120;// 每一行的间隔
	public float driftx;// x偏移量
	public float drifty;// y偏移量
	private float drift_r;
	public boolean showprogress;// 滑动时显示进度
	public int temp = 0;
	private LrcInfo info = new LrcInfo();
	public List<Long> times = new ArrayList<Long>();
	public List<String> texts = new ArrayList<String>();
	private String path="/sdcard/天后sad.lrc";
	
	public long songAlltime;
	
	public boolean changeLrc=true;

	public LyricView(Context context) {
		super(context);
		//getLrc();
		init();
	}

	public LyricView(Context context, AttributeSet attr) {
		super(context, attr);
		//getLrc();
		init();
	}

	public LyricView(Context context, AttributeSet attr, int i) {
		super(context, attr, i);
		//getLrc();
		init();
	}

	
	
	private void getLrc() {

		LrcParser lp = new LrcParser();

		try {
			info = lp.parser(path);
			// System.out.println(info.getTitle());
			times = info.getTimes();
			// times.add(0, (long) 0);
			texts = info.getTexts();
			texts.add(0, "歌词真好");
		} catch (Exception e) {
			System.out.println("歌词出错了");
			
			Log.e("", e.getMessage());

		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void setTextScaleX(float size) {
		// TODO Auto-generated method stub
		super.setTextScaleX(size);
	}

	private void init() {
		
		
		
		
		setFocusable(true);
		// 非高亮部分
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(50);
		mPaint.setColor(Color.WHITE);
		mPaint.setTypeface(Typeface.SERIF);
		// 高亮部分 当前歌词
		mPathPaint = new Paint();
		mPathPaint.setAntiAlias(true);
		mPathPaint.setTextSize(60);
		mPathPaint.setColor(Color.RED);
		mPathPaint.setTypeface(Typeface.SANS_SERIF);
	}

	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		if(changeLrc){
		
		getLrc();
		
		System.out.println(songAlltime);
		times.add(songAlltime);
		
		
		}
		
		changeLrc=false;

		// 滑动相关
		// 显示进度相关
		int j = (int) (-drifty / 40);
		if (temp < j) {
			temp++;
		} else if (temp > j) {
			temp--;
		}

		if (index + temp >= 0 && index + temp < times.size() - 1)
			drift_r = drifty;

		canvas.drawColor(0xEFeffff);
		Paint p = mPaint;
		Paint p2 = mPathPaint;
		p.setTextAlign(Paint.Align.CENTER);

		if (index == -1)
			return;
		p2.setTextAlign(Paint.Align.CENTER);
		// 先画当前行，之后再画他的前面和后面，这样就保持了当前行在中间的位置
		canvas.drawText(texts.get(index), mX, middleY + drift_r, p2);
		if (showprogress && index + temp < times.size() - 1) {
			p2.setTextAlign(Paint.Align.LEFT);
			if (index + temp >= 0) {
				canvas.drawText(TimeParseTool.MsecParseTime(String
						.valueOf(times.get(index + temp))), 0, middleY, p2);
			} else
				canvas.drawText("00:00", 0, middleY, p2);
			canvas.drawLine(0, middleY + 1, mX * 2, middleY + 1, p2);
		}
		float tempY = middleY + drift_r;
		// 画出本句之前的句子
		for (int i = index - 1; i >= 0; i--) {
			// 向上推移
			tempY = tempY - DY;
			if (tempY < 0) {
				break;
			}
			canvas.drawText(texts.get(i), mX, tempY, p);
		}
		tempY = middleY + drift_r;
		// 画出本句之后的句子
		for (int i = index + 1; i < texts.size(); i++) {
			// 向下推移
			tempY = tempY + DY;
			if (tempY > mY) {
				break;
			}
			canvas.drawText(texts.get(i), mX, tempY, p);
		}

	}

	protected void onSizeChanged(int w, int h, int ow, int oh) {
		super.onSizeChanged(w, h, ow, oh);
		mX = w * 0.5f;// 屏幕中心坐标(转换为float?)
		mY = h;
		middleY = h * 0.3f;
	}

	/**
	 * @author younger
	 * @param CurrentPosition
	 *            当前歌词的时间轴
	 * @return drift 可以返回数据（已经废弃）
	 */
	public float updateindex(long CurrentPosition) {

		System.out.println(CurrentPosition + "");

		index = SearchIndex(times, CurrentPosition);

		if (index == 0) {
			currentDuringTime = times.get(index);
		} else {
			
			currentDuringTime = times.get(index) - times.get(index - 1);
			
			if(index>=times.size()){
			System.out.println(currentDuringTime+"");
			}
		}

		/*
		 * if (index == 0) { currentDuringTime = times.get(index); index++; }
		 * else { if (CurrentPosition >= times.get(index) && CurrentPosition <
		 * times.get(index + 1)) { currentDuringTime = times.get(index + 1) -
		 * times.get(index); index++; drifty = 0; driftx = 0; } else { index =
		 * SearchIndex(times, CurrentPosition); System.out.println(index+"");
		 * currentDuringTime = times.get(index) - times.get(index - 1); drifty =
		 * 0; driftx = 0; } }
		 */
		drifty = 0;
		driftx = 0;
		if (drifty > -40.0)
			drifty = (float) (drifty - 40.0 / (currentDuringTime / 100));

		if (index == -1)
			return -1;
		return drifty;
	}

	public boolean repair() {
		if (index <= 0) {
			index = 0;
			return false;
		}
		if (index > times.size() - 1)
			index = times.size() - 1;
		return true;
	}

	private int SearchIndex(List<Long> list, long l) {
		int i;

		for (i = 0; i < list.size(); i++) {
			if (l < list.get(i))
				break;
		}
		return i;
	}

}
