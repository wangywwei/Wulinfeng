package com.hxwl.wulinfeng.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;

public class TimeCountUtil
        extends CountDownTimer {
    private TextView btn;
    private Activity mActivity;

    public TimeCountUtil(Activity paramActivity, long paramLong1, long paramLong2, TextView paramTextView) {
        super(paramLong1, paramLong2);
        this.mActivity = paramActivity;
        this.btn = paramTextView;
    }

    @SuppressLint({"NewApi"})
    public void onFinish() {
        this.btn.setBackground(MakerApplication.instance.getResources().getDrawable(R.drawable.send_red));
        this.btn.setText("重新获取");
        this.btn.setClickable(true);
    }

    @SuppressLint({"NewApi"})
    public void onTick(long paramLong) {
        this.btn.setClickable(false);
        this.btn.setText(paramLong / 1000L + "秒重新发送");
        SpannableString localSpannableString = new SpannableString(this.btn.getText().toString());
        localSpannableString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, 3, 17);
        this.btn.setText(localSpannableString);
    }
}
