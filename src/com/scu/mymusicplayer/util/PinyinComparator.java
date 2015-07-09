package com.scu.mymusicplayer.util;

import java.util.Comparator;

import com.scu.mymusicplayer.bean.MusicInfo;

public class PinyinComparator implements Comparator<MusicInfo> {

	public int compare(MusicInfo o1, MusicInfo o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
