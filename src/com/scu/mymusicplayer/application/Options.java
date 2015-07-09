package com.scu.mymusicplayer.application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.scu.mymusicplayer.R;

import android.graphics.Bitmap;

public class Options {
	public static DisplayImageOptions getListOptions() { 
		DisplayImageOptions options = new DisplayImageOptions.Builder()  
                .showImageOnLoading(R.drawable.contenbar)  
                .showImageForEmptyUri(R.drawable.contenbar)  
                // ����ͼƬ����/��������д���ʱ����ʾ��ͼƬ  
                .showImageOnFail(R.drawable.contenbar)  
                // �������ص�ͼƬ�Ƿ񻺴����ڴ���  
                .cacheInMemory(true)  
                // �������ص�ͼƬ�Ƿ񻺴���SD����  
                .cacheOnDisc(true)  
                // ����ͼƬ����εı��뷽ʽ��ʾ  
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)  
                // ����ͼƬ�Ľ�������  
                .bitmapConfig(Bitmap.Config.RGB_565)  
                .resetViewBeforeLoading(true)// ����ͼƬ������ǰ�Ƿ����ã���λ  
                .displayer(new RoundedBitmapDisplayer(20))//�Ƿ�����ΪԲ�ǣ�����Ϊ����  
                .displayer(new FadeInBitmapDisplayer(100))// ����  
                .build();  
        return options;  
		
	}

}
