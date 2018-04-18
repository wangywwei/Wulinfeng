package com.hxwl.wulinfeng.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * Created by Allen on 2017/6/13.
 */

public class SideBarTouch extends View{
    //SideBar上显示的字母和#号
    private static final String[] CHARACTERS = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    //SideBar的高度
    private int width;
    //SideBar的宽度
    private int height;
    //SideBar中每个字母的显示区域的高度
    private float cellHeight;
    //画字母的画笔
    private Paint characterPaint;
    //SideBar上字母绘制的矩形区域
    private Rect textRect;
    //手指触摸在SideBar上的横纵坐标
    private float touchY;
    private float touchX;

    private OnSelectListener listener;

    public SideBarTouch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SideBarTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SideBarTouch(Context context) {
        super(context);
        init(context);
    }

    //初始化操作
    private void init(Context context){
        textRect = new Rect();
        characterPaint = new Paint();
        characterPaint.setColor(Color.parseColor("#6699ff"));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){ //在这里测量SideBar的高度和宽度
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            //SideBar的高度除以需要显示的字母的个数，就是每个字母显示区域的高度
            cellHeight = height * 1.0f / CHARACTERS.length;
            //根据SideBar的宽度和每个字母显示的高度，确定绘制字母的文字大小，这样处理的好处是，对于不同分辨率的屏幕，文字大小是可变的
            int textSize = (int) ((width > cellHeight ? cellHeight : width) * (3.0f / 4));
            characterPaint.setTextSize(textSize);
        }
    }

    //画出SideBar上的字母
    private void drawCharacters(Canvas canvas){
        for(int i = 0; i < CHARACTERS.length; i++){
            String s = CHARACTERS[i];
            //获取画字母的矩形区域
            characterPaint.getTextBounds(s, 0, s.length(), textRect);
            //根据上一步获得的矩形区域，画出字母
            canvas.drawText(s,
                    (width - textRect.width()) / 2f,
                    cellHeight * i + (cellHeight + textRect.height()) / 2f,
                    characterPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCharacters(canvas);
    }

    //根据手指触摸的坐标，获取当前选择的字母
    private String getHint(){
        int index = (int) (touchY / cellHeight);
        if(index >= 0 && index < CHARACTERS.length){
            return CHARACTERS[index];
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //获取手指触摸的坐标
                touchX = event.getX();
                touchY = event.getY();
                if(listener != null && touchX > 0){
                    listener.onSelect(getHint());
                }
                if(listener != null && touchX < 0){
                    listener.onMoveUp(getHint());
                }
                return true;
            case MotionEvent.ACTION_UP:
                touchY = event.getY();
                if(listener != null){
                    listener.onMoveUp(getHint());
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    //监听器，监听手指在SideBar上按下和抬起的动作
    public interface OnSelectListener{
        void onSelect(String s);
        void onMoveUp(String s);
    }

    //设置监听器
    public void setOnSelectListener(OnSelectListener listener){
        this.listener = listener;
    }

}
