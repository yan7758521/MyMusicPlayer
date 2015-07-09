package com.scu.mymusicplayer.adpter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.bean.MusicInfo;
import com.scu.mymusicplayer.util.CharacterParser;
import com.scu.mymusicplayer.util.PinyinComparator;

public class ListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private static List<MusicInfo> mlistData = new ArrayList<MusicInfo>();
	private static SimpleDateFormat nowTimeFormat = null;
	private HashMap<String, Integer> alphaIndexer;
	private CharacterParser characterParser;
	private PinyinComparator pinyinComparator;
	

	public ListAdapter(Context context, List<MusicInfo> list) {
		mInflater = LayoutInflater.from(context);
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		// mlistData = list;
		
		filledData(list);
	}

	private void filledData(List<MusicInfo> datas) {
		int total = datas.size();
		for (int i = 0; i < total; i++) {
			MusicInfo music = datas.get(i);
			MusicInfo sortModel = new MusicInfo(music.getId(),
					music.getM_musicId(), music.getM_musicPath(),
					music.getM_musicTitle(), music.getM_musicSinger(),
					music.getM_duration());
			// 汉字转换成拼音
			String singername = sortModel.getM_musicSinger();
			if (singername != null) {
				if (singername.equalsIgnoreCase("<unknows>")) {
					String pinyin2 = characterParser.getSelling(sortModel
							.getM_musicTitle());
					String sortString = pinyin2.substring(0, 1).toUpperCase();
					// 正则表达式，判断首字母是否是英文字母
					if (sortString.matches("[A-Z]")) {
						sortModel.setSortLetters(sortString.toUpperCase());
					} else {
						sortModel.setSortLetters("#");
					}
				} else {
					String pinyin = characterParser.getSelling(sortModel
							.getM_musicSinger());
					String sortString = pinyin.substring(0, 1).toUpperCase();
					// 正则表达式，判断首字母是否是英文字母
					if (sortString.matches("[A-Z]")) {
						sortModel.setSortLetters(sortString.toUpperCase());
					} else {
						sortModel.setSortLetters("#");
					}
				}
			}

			else {
				sortModel.setSortLetters("#");
			}
			mlistData.add(sortModel);
		}
		// 根据a-z进行排序
		Collections.sort(mlistData, pinyinComparator);
	}

	public List<MusicInfo> getList() {
		return mlistData;
	}

	public int getCount() {
		return mlistData.size();
	}

	public MusicInfo getItem(int position) {
		return mlistData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;

		// 用缓存创建一个新的item
		if (view == null) {
			view = mInflater.inflate(R.layout.music_list, null);
			holder = new ViewHolder();
			holder.alpha = (TextView) view.findViewById(R.id.alpha);
			holder.img = (ImageView) view.findViewById(R.id.mp3);
			holder.textItem = (TextView) view.findViewById(R.id.item_title);
			holder.textItem1 = (TextView) view.findViewById(R.id.item_singer);
			holder.textItem2 = (TextView) view.findViewById(R.id.itemText2);

			view.setTag(holder);

		} else {
			holder = (ViewHolder) view.getTag();
		}

		int nowTime = mlistData.get(position).getM_duration();
		nowTimeFormat = new SimpleDateFormat("mm:ss");
		String ms1 = nowTimeFormat.format(nowTime);

		// 获得position位置的信息
		holder.img.setImageResource(R.drawable.mp31);
		holder.textItem.setText(mlistData.get(position).getM_musicTitle());
		if (mlistData.get(position).getM_musicSinger()
				.equalsIgnoreCase("<unknown>")) {
			holder.textItem1.setText("");
		} else {
			holder.textItem1.setText(mlistData.get(position).getM_musicSinger()
					+ "--");
		}
		holder.textItem2.setText(ms1);

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(mlistData.get(position).getSortLetters());
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		// System.out.println(mlistData.get(i).getMusicTitle());
		// System.out.println(mlistData.get(i).getMusicSinger());
		return view;
	}

	public final class ViewHolder {
		public TextView alpha;
		public ImageView img;
		public TextView textItem;
		public TextView textItem1;
		public TextView textItem2;
	}

	public int getSectionForPosition(int position) {
		return mlistData.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@SuppressLint("DefaultLocale")
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = mlistData.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}
}
