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
	private long currentDuringTime;// ��ǰ��ʳ���ʱ��
	private float middleY;// Y���м�
	private final int DY = 120;// ÿһ�еļ��
	public float driftx;// xƫ����
	public float drifty;// yƫ����
	private float drift_r;
	public boolean showprogress;// ����ʱ��ʾ����
	public int temp = 0;
	private LrcInfo info = new LrcInfo();
	public List<Long> times = new ArrayList<Long>();
	public List<String> texts = new ArrayList<String>();
	private String path="/sdcard/���sad.lrc";
	
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
			texts.add(0, "������");
		} catch (Exception e) {
			System.out.println("��ʳ�����");
			
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
		// �Ǹ�������
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(50);
		mPaint.setColor(Color.WHITE);
		mPaint.setTypeface(Typeface.SERIF);
		// �������� ��ǰ���
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

		// �������
		// ��ʾ�������
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
		// �Ȼ���ǰ�У�֮���ٻ�����ǰ��ͺ��棬�����ͱ����˵�ǰ�����м��λ��
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
		// ��������֮ǰ�ľ���
		for (int i = index - 1; i >= 0; i--) {
			// ��������
			tempY = tempY - DY;
			if (tempY < 0) {
				break;
			}
			canvas.drawText(texts.get(i), mX, tempY, p);
		}
		tempY = middleY + drift_r;
		// ��������֮��ľ���
		for (int i = index + 1; i < texts.size(); i++) {
			// ��������
			tempY = tempY + DY;
			if (tempY > mY) {
				break;
			}
			canvas.drawText(texts.get(i), mX, tempY, p);
		}

	}

	protected void onSizeChanged(int w, int h, int ow, int oh) {
		super.onSizeChanged(w, h, ow, oh);
		mX = w * 0.5f;// ��Ļ��������(ת��Ϊfloat?)
		mY = h;
		middleY = h * 0.3f;
	}

	/**
	 * @author younger
	 * @param CurrentPosition
	 *            ��ǰ��ʵ�ʱ����
	 * @return drift ���Է������ݣ��Ѿ�������
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
