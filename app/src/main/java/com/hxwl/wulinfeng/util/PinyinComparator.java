package com.hxwl.wulinfeng.util;

import com.hxwl.wulinfeng.bean.PlayBean;

import java.util.Comparator;

/**
 * 字母排序比较器
 * @author Allen
 *
 */
public class PinyinComparator implements Comparator<PlayBean>
{

	public int compare(PlayBean o1, PlayBean o2)
	{
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) 
			return -1;
		else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) 
			return 1;
	    else 
			return o1.getSortLetters().compareTo(o2.getSortLetters());
	}

}
