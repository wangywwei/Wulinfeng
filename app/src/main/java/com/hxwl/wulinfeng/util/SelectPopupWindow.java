package com.hxwl.wulinfeng.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.hxwl.wulinfeng.R;

import java.util.ArrayList;
import java.util.List;


public class SelectPopupWindow extends PopupWindow {

    private Button item_popupwindows_cancel;           //弹窗取消按钮
    private View menuview;
	private ListView lv;

	
	/** 
     * 回调接口对象 
     */  
    private OnPopupWindowClickListener listener;  
    /** 
     * ArrayAdapter对象 
     */  
    private ArrayAdapter adapter;  
    /** 
     * ListView的数据源 
     */  
    private List<String> list = new ArrayList<String>(); 
	
	
    public SelectPopupWindow(Activity context, final String[] title){
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuview = inflater.inflate(R.layout.dialog_takephoto,null);
        item_popupwindows_cancel = (Button) menuview.findViewById(R.id.item_popupwindows_cancel);   //取消按钮

        /**
         * 取消按钮销毁事件
         */
        item_popupwindows_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        
        lv = (ListView) menuview.findViewById(R.id.pop_list);
        PopListAdapter popListAdapter=new PopListAdapter(context, title);
        lv.setAdapter(popListAdapter);  
          
        //ListView的点击事件  
        lv.setOnItemClickListener(new OnItemClickListener() {  
  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
            	SelectPopupWindow.this.dismiss();
                if(listener != null){  
                    listener.onPopupWindowItemClick(position);  
                }  
            }  
        }); 
        
        
        //设置SelectPicPopupWindow的View
        this.setContentView(menuview);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        //修改高度显示，解决被手机底部虚拟键挡住的问题  
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //menuview添加ontouchlistener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        menuview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = menuview.findViewById(R.id.ll_popup).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });


    }
    
    
    /** 
     * 为PopupWindow设置回调接口 
     * @param listener 
     */  
    public void setOnPopupWindowClickListener(OnPopupWindowClickListener listener){  
        this.listener = listener;  
    }  
      
      
    /** 
     * 回调接口.供外部调用 
     * @author xiaanming 
     * 
     */  
    public interface OnPopupWindowClickListener{  
        /** 
         * 当点击PopupWindow的ListView 的item的时候调用此方法，用回调方法的好处就是降低耦合性 
         * @param position 位置 
         */  
        void onPopupWindowItemClick(int position);  
    }
    
    
  //pop适配器
    public class PopListAdapter extends BaseAdapter{
    	private Context context;
    	private String[] title;

		public PopListAdapter(Context context,String[] title) {
			this.context=context;
			this.title=title;
			
		}

		@Override
		public int getCount() {
			return title.length;
		}

		@Override
		public Object getItem(int position) {
			return title[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.pop_select_item, null);
				viewHolder.pop_item_title=(TextView) convertView.findViewById(R.id.pop_item_title);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.pop_item_title.setText(title[position]);
			
			return convertView;
		}
    	
    }
    
    public class ViewHolder{
    	private TextView pop_item_title;
    }

}
