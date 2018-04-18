package com.hxwl.wulinfeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/8/12.
 */
public class MyGrideview extends GridView {
    public MyGrideview(Context context, AttributeSet attrs) {
            super(context, attrs);
       }
     public MyGrideview(Context context) {
             super(context);
        }

      public MyGrideview(Context context, AttributeSet attrs, int defStyle) {
          super(context, attrs, defStyle);
      }

            @Override
       public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

             int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                     MeasureSpec.AT_MOST);
           super.onMeasure(widthMeasureSpec, expandSpec);
         }
  }

