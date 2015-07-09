package com.scu.mymusicplayer.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.scu.mymusicplayer.bean.SongInfo;


public class JosnPaserEngine {
	
	private static String img_paihang_url;
	
	
	public static List<SongInfo> getSongs(String strJson) {
	
		
		List<SongInfo> songs = new ArrayList<SongInfo>();
		try {
			//将JSON字符串转换为JSON数组
			System.out.println(strJson);
			
			
			JSONObject jsonObject1=new JSONObject(strJson);
			JSONObject jsonObject2=new JSONObject(jsonObject1.getString("showapi_res_body"));
			JSONObject jsonObject3=new JSONObject(jsonObject2.getString("pagebean"));
			String contentStr=jsonObject3.getString("contentlist");
			
			JSONArray jsonArray=new JSONArray(contentStr);
			//遍历JSON数组
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				
				SongInfo song=new SongInfo();
				
				song.setAlbumid(jsonObject.getString("albumid"));
				song.setAlbumname(jsonObject.getString("albumname"));
				song.setBigPicture_url(jsonObject.getString("albumpic_big"));
				song.setSmallPicture_url(jsonObject.getString("albumpic_small"));
				song.setDownUrl(jsonObject.getString("downUrl"));
				song.setSingerName(jsonObject.getString("singername"));
				song.setSongId(jsonObject.getString("songid"));
				song.setSongName(jsonObject.getString("songname"));
				
				
				songs.add(song);
				
				
				
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return songs;
	}
	
	
	
	public static void getPaiHang(String strJson,List<SongInfo> songs) {

		
		try {
			//将JSON字符串转换为JSON数组
			System.out.println(strJson);
			
			
			JSONObject jsonObject1=new JSONObject(strJson);
			JSONObject jsonObject2=new JSONObject(jsonObject1.getString("showapi_res_body"));
			JSONObject jsonObject3=new JSONObject(jsonObject2.getString("pagebean"));
			img_paihang_url=jsonObject3.getString("picUrl");
			String contentStr=jsonObject3.getString("songlist");
			
			JSONArray jsonArray=new JSONArray(contentStr);
			//遍历JSON数组
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				
				SongInfo song=new SongInfo();
				
				song.setAlbumid(jsonObject.getString("albumid"));
				song.setAlbumname(jsonObject.getString("albumname"));
				song.setBigPicture_url("");
				song.setSmallPicture_url("");
				song.setDownUrl(jsonObject.getString("downUrl"));
				song.setSingerName(jsonObject.getString("singername"));
				song.setSongId(jsonObject.getString("songid"));
				song.setSongName(jsonObject.getString("songname"));
				
				
				songs.add(song);
				
				
				
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getUrl(){
		return img_paihang_url;
	}
	

}
