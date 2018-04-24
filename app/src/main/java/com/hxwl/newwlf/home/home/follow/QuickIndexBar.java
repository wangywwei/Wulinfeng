package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hxwl.wulinfeng.R;

public class QuickIndexBar extends View {
	private Paint mPaint;
	private int mWidth;
	// 格子的高度
	private float mCellHieght;
	private String[] mIndexArr = { "☆","A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z","#" };

	// 在代码中new对象的时候调用
	public QuickIndexBar(Context context) {
		this(context, null);
	}

	// 在xml中声明该控件的时候调用
	public QuickIndexBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	// 在xml中声明控件并且使用样式的时候调用
	public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	// onMessure()测量当前View的宽高，方法后执行的
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = getMeasuredWidth();
		mCellHieght = getMeasuredHeight() * 1.0F / 28;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void init() {
		mPaint = new Paint();
		// 设置是否抗锯齿
		mPaint.setAntiAlias(true);
		// 设置画笔绘制的颜色
		mPaint.setColor(getResources().getColor(R.color.rgb_000000));
		// 设置绘制的文本的大小
		mPaint.setTextSize(24);

		// 设置绘制文本的起点坐标是当前文本的下边缘的正中心
		mPaint.setTextAlign(Align.CENTER);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		for (int i = 0; i < mIndexArr.length; i++) {
			float x = mWidth / 2;
			float y = mCellHieght / 2 + getTextHeight(mIndexArr[i]) / 2 + i
					* mCellHieght;
			mPaint.setColor(i == mLastIndex ? getResources().getColor(R.color.rgb_BA2B2C) : getResources().getColor(R.color.rgb_000000));
			canvas.drawText(mIndexArr[i], x, y, mPaint);
		}
		super.onDraw(canvas);
	}

	private int mLastIndex = -1;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			// 按下
			case MotionEvent.ACTION_DOWN:
				// 移动
			case MotionEvent.ACTION_MOVE:
				int index = (int) (event.getY() / mCellHieght);

				// 字母变化 如果触摸的两个字母不一样就将字母提供出去
				if (index != mLastIndex) {
					if (index >= 0 && index < mIndexArr.length) {
						if (mListenner != null)
							mListenner.onTouchLetter(mIndexArr[index]);
					}
				}
				// 重新绘制(重新调用一次onDraw()方法)
				invalidate();
				mLastIndex = index;
				break;
			// 抬起
			case MotionEvent.ACTION_UP:
				mLastIndex = -1;
				break;
		}

		// 将触摸事件消耗掉
		return true;
	}

	// 获取文本的高度
	private int getTextHeight(String text) {
		Rect bounds = new Rect();
		mPaint.getTextBounds(text, 0, 1, bounds);
		return bounds.height();
	}

	// 通过接口回调的方式将触摸字母监听器提供出去
	public interface onTouchLetterListenner {
		public abstract void onTouchLetter(String letter);
	}

	private onTouchLetterListenner mListenner;

	public void setOnTouchLetterListenner(onTouchLetterListenner listenner) {
		this.mListenner = listenner;
	}

}