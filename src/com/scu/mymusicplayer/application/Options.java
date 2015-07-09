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
                // 设置图片加载/解码过程中错误时候显示的图片  
                .showImageOnFail(R.drawable.contenbar)  
                // 设置下载的图片是否缓存在内存中  
                .cacheInMemory(true)  
                // 设置下载的图片是否缓存在SD卡中  
                .cacheOnDisc(true)  
                // 设置图片以如何的编码方式显示  
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)  
                // 设置图片的解码类型  
                .bitmapConfig(Bitmap.Config.RGB_565)  
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位  
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少  
                .displayer(new FadeInBitmapDisplayer(100))// 淡入  
                .build();  
        return options;  
		
	}

}
