package com.hxwl.wulinfeng.wulin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;

public class ImageListGridViewAdapter extends BaseAdapter {
	private List<String> mDataList = new ArrayList<String>();
	private Context mContext;

	public ImageListGridViewAdapter(Context context, List<String> dataList) {
		this.mContext = context;
		this.mDataList = dataList;
	}

	public int getCount() {
		// 多返回一个用于展示添加图标
		if (mDataList == null) {
			return 0;
		} else {
			return mDataList.size();
		}
	}

	public Object getItem(int position) {
		return mDataList.get(position);

	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	public View getView(int position, View convertView, ViewGroup parent) {
		// 所有Item展示不满一页，就不进行ViewHolder重用了，避免了一个拍照以后添加图片按钮被覆盖的奇怪问题
		convertView = View.inflate(mContext, R.layout.item_publish, null);
		ImageView imageIv = (ImageView) convertView
				.findViewById(R.id.item_grid_image);

		String item = mDataList.get(position);
		if(item != null && !TextUtils.isEmpty(item)){
			ImageUtils.getPic(item,imageIv,mContext);
		}
		return convertView;
	}

}
