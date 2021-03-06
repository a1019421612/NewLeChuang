package com.hbdiye.newlechuangsmart.util;

import com.hbdiye.newlechuangsmart.bean.SortModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		if (o1.inital.equals("@")
				|| o2.inital.equals("#")) {
			return 1;
		} else if (o1.inital.equals("#")
				|| o2.inital.equals("@")) {
			return -1;
		} else {
			return o1.inital.compareTo(o2.inital);
		}
	}

}
