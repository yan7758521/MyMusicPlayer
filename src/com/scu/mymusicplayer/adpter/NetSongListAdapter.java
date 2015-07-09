package com.scu.mymusicplayer.adpter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scu.mymusicplayer.R;
import com.scu.mymusicplayer.application.Options;
import com.scu.mymusicplayer.bean.SongInfo;

public class NetSongListAdapter extends BaseAdapter {

	private List<SongInfo> songLists;
	private LayoutInflater mInflater;
	private Context context;
	DisplayImageOptions option = Options.getListOptions();
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public NetSongListAdapter(Context context, List<SongInfo> list) {
		this.context=context;
		mInflater = LayoutInflater.from(context);
		this.songLists = list;
	}

	public List<SongInfo> getList() {
		return songLists;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return songLists.size();
	}

	@Override
	public SongInfo getItem(int position) {
		// TODO Auto-generated method stub
		return songLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		MyViewHolder holder = null;

		// 用缓存创建一个新的item
		if (view == null) {
			view = mInflater.inflate(R.layout.songitems, null);
			holder = new MyViewHolder();
			holder.Song_img = (ImageView) view.findViewById(R.id.song_pic);
			imageLoader.displayImage(songLists.get(position)
					.getSmallPicture_url(), holder.Song_img, option);

			holder.song_name = (TextView) view.findViewById(R.id.song_song);
			
			holder.song_name.setText(songLists.get(position).getSingerName()
					+ "---" + songLists.get(position).getSongName());
			
			holder.song_add = (ImageButton) view.findViewById(R.id.song_add);
			
			holder.song_add.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					System.out.println("点击了加号");

				}
			});

			view.setTag(holder);

		} else {
			holder = (MyViewHolder) view.getTag();
		}
		return view;
	}

	public final class MyViewHolder {
		public ImageView Song_img;
		public TextView song_name;
		public ImageButton song_add;
	}

}
