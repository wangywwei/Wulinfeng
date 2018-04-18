package com.hxwl.common.tencentplay.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hxwl.common.tencentplay.editor.TCHorizontalScrollView;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

public class BeautySettingPannel extends FrameLayout implements SeekBar.OnSeekBarChangeListener {

    public static final int BEAUTYPARAM_EXPOSURE = 0;
    public static final int BEAUTYPARAM_BEAUTY = 1;
    public static final int BEAUTYPARAM_WHITE = 2;
    public static final int BEAUTYPARAM_FACE_LIFT = 3;
    public static final int BEAUTYPARAM_BIG_EYE = 4;
    public static final int BEAUTYPARAM_FILTER = 5;
    public static final int BEAUTYPARAM_MOTION_TMPL = 6;
    public static final int BEAUTYPARAM_GREEN = 7;

    static public class BeautyParams{
        public float mExposure = 0;
        public int mBeautyLevel = 5;
        public int mWhiteLevel = 3;
        public int mFaceSlimLevel;
        public int mBigEyeLevel;
        public Bitmap mFilterBmp;
        public String mMotionTmplPath;
        public String mGreenFile;
    }

    public interface IOnBeautyParamsChangeListener{
        void onBeautyParamsChange(BeautyParams params, int key);
    }

    private SeekBar mBeautySeekBar;
    private SeekBar mWhiteningSeekBar;
    private SeekBar mExposureSeekBar;
    private SeekBar mBigEyeSeekBar;
    private SeekBar mFaceLiftSeekBar;
    TCHorizontalScrollView mFilterSV;
    private ArrayAdapter<Integer> mFilterAdapter;
    TXHorizontalPickerView mGreenScreenPicker;
    ArrayAdapter<String> mGreenScreenAdapter;
    TXHorizontalPickerView mDynamicEffectPicker;
    ArrayAdapter<String> mDynamicEffectAdapter;
    private Context mContext;
    private LinearLayout llyt_lvjing_pannel;
    private LinearLayout llyt_beauty_pannel;
    private RelativeLayout rlyt_beauty;
    private RelativeLayout rlyt_lvjing;
    private CheckedTextView ctv_beauty;
    private CheckedTextView ctv_lvjing;

    private IOnBeautyParamsChangeListener mBeautyChangeListener;

    public BeautySettingPannel(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.beauty_pannel, this);
        mContext = context;
        initView(view);
    }

    public void setBeautyParamsChangeListener(IOnBeautyParamsChangeListener listener) {
        mBeautyChangeListener = listener;
    }

    private void initView(View view) {

        mBeautySeekBar = (SeekBar) view.findViewById(R.id.beauty_seekbar);
        mBeautySeekBar.setOnSeekBarChangeListener(this);

        mExposureSeekBar = (SeekBar) view.findViewById(R.id.exposure_seekbar);
        mExposureSeekBar.setOnSeekBarChangeListener(this);

        mWhiteningSeekBar = (SeekBar) view.findViewById(R.id.whitening_seekbar);
        mWhiteningSeekBar.setOnSeekBarChangeListener(this);

        mBigEyeSeekBar = (SeekBar) view.findViewById(R.id.bigeye_seekbar);
        mBigEyeSeekBar.setOnSeekBarChangeListener(this);

        mFaceLiftSeekBar = (SeekBar) view.findViewById(R.id.facelift_seekbar);
        mFilterSV = (TCHorizontalScrollView) view.findViewById(R.id.filter_sv);
        llyt_beauty_pannel = (LinearLayout) view.findViewById(R.id.llyt_beauty_pannel);
        llyt_lvjing_pannel = (LinearLayout) view.findViewById(R.id.llyt_lvjing_pannel);
        rlyt_beauty = (RelativeLayout) view.findViewById(R.id.rlyt_beauty);
        rlyt_lvjing = (RelativeLayout) view.findViewById(R.id.rlyt_lvjing);
        ctv_beauty = (CheckedTextView) view.findViewById(R.id.ctv_beauty);
        ctv_lvjing = (CheckedTextView) view.findViewById(R.id.ctv_lvjing);
        rlyt_beauty.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llyt_lvjing_pannel.setVisibility(View.GONE);
                llyt_beauty_pannel.setVisibility(View.VISIBLE);
                ctv_beauty.setChecked(true);
                ctv_lvjing.setChecked(false);
                ctv_lvjing.setTextColor(getResources().getColor(R.color.white));
                ctv_beauty.setTextColor(getResources().getColor(R.color.yellow_new));
            }
        });
        rlyt_lvjing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llyt_beauty_pannel.setVisibility(View.GONE);
                llyt_lvjing_pannel.setVisibility(View.VISIBLE);
                ctv_beauty.setChecked(false);
                ctv_lvjing.setChecked(true);
                ctv_lvjing.setTextColor(getResources().getColor(R.color.yellow_new));
                ctv_beauty.setTextColor(getResources().getColor(R.color.white));
            }
        });
        mFaceLiftSeekBar.setOnSeekBarChangeListener(this);

        final ArrayList<Integer> filterIDList = new ArrayList<Integer>();
        filterIDList.add(R.drawable.dp_orginal);
        filterIDList.add(R.drawable.dp_langman);
        filterIDList.add(R.drawable.dp_qingxin);
        filterIDList.add(R.drawable.dp_weimei);
        filterIDList.add(R.drawable.dp_fennen);
        filterIDList.add(R.drawable.dp_huaijiu);
        filterIDList.add(R.drawable.dp_landiao);
        filterIDList.add(R.drawable.dp_qingliang);
        filterIDList.add(R.drawable.dp_rixi);
        mFilterAdapter = new ArrayAdapter<Integer>(mContext,0, filterIDList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.filter_layout,null);
                }
                ImageView view = (ImageView) convertView.findViewById(R.id.filter_image);
                if (position == 0) {
                    ImageView view_tint = (ImageView) convertView.findViewById(R.id.filter_image_tint);
                    if (view_tint != null)
                        view_tint.setVisibility(View.VISIBLE);
                }
                view.setTag(position);
                view.setImageDrawable(getResources().getDrawable(getItem(position)));
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = (int) view.getTag();
                        selectFilter(index);
                        setFilter(index);
                    }
                });
                return convertView;

            }
        };
        mFilterSV.setAdapter(mFilterAdapter);

        //绿幕
        ArrayList<String> greenFilterList = new ArrayList<>();
        greenFilterList.add("无绿幕");
        greenFilterList.add("绿幕1");
        mGreenScreenPicker = (TXHorizontalPickerView) view.findViewById(R.id.greenPicker);
        mGreenScreenAdapter = new ArrayAdapter<String>(mContext, 0, greenFilterList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String value = getItem(position);
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(android.R.layout.simple_list_item_1,null);
                }
                TextView view = (TextView) convertView.findViewById(android.R.id.text1);
                view.setTag(position);
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                view.setText(value);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = (int) view.getTag();
                        ViewGroup group = (ViewGroup)mGreenScreenPicker.getChildAt(0);
                        for (int i = 0; i < mGreenScreenAdapter.getCount(); i++) {
                            View v = group.getChildAt(i);
                            if (v instanceof TextView) {
                                if (i == index) {
                                    ((TextView) v).setTextColor(Color.BLACK);
                                } else {
                                    ((TextView) v).setTextColor(Color.GRAY);
                                }
                            }
                        }
                        setGreenScreen(index);
                    }
                });
                return convertView;
            }
        };
        mGreenScreenPicker.setAdapter(mGreenScreenAdapter);
        mGreenScreenPicker.setClicked(0);

        //动效
        ArrayList<String> dynamicEffectFilterList = new ArrayList<>();
        dynamicEffectFilterList.add("无动效");
        dynamicEffectFilterList.add("动效1");
        dynamicEffectFilterList.add("动效2");
        mDynamicEffectPicker = (TXHorizontalPickerView) view.findViewById(R.id.dynamicEffectPicker);
        mDynamicEffectAdapter = new ArrayAdapter<String>(mContext, 0, dynamicEffectFilterList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String value = getItem(position);
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(android.R.layout.simple_list_item_1,null);
                }
                TextView view = (TextView) convertView.findViewById(android.R.id.text1);
                view.setTag(position);
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                view.setText(value);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = (int) view.getTag();
                        ViewGroup group = (ViewGroup)mDynamicEffectPicker.getChildAt(0);
                        for (int i = 0; i < mDynamicEffectAdapter.getCount(); i++) {
                            View v = group.getChildAt(i);
                            if (v instanceof TextView) {
                                if (i == index) {
                                    ((TextView) v).setTextColor(Color.BLACK);
                                } else {
                                    ((TextView) v).setTextColor(Color.GRAY);
                                }
                            }
                        }
                        setDynamicEffect(index);
                    }
                });
                return convertView;
            }
        };
        mDynamicEffectPicker.setAdapter(mDynamicEffectAdapter);
        mDynamicEffectPicker.setClicked(0);
    }

    private void selectFilter(int index) {
        ViewGroup group = (ViewGroup)mFilterSV.getChildAt(0);
        for (int i = 0; i < mFilterAdapter.getCount(); i++) {
            View v = group.getChildAt(i);
            ImageView IVTint = (ImageView) v.findViewById(R.id.filter_image_tint);
            if (IVTint != null) {
                if (i == index) {
                    IVTint.setVisibility(View.VISIBLE);
                } else {
                    IVTint.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    //设置滤镜
    private void setFilter(int index) {
        Bitmap bmp = null;
        switch (index) {
            case 1:
                bmp = decodeResource(getResources(), R.drawable.filter_langman);
                break;
            case 2:
                bmp = decodeResource(getResources(), R.drawable.filter_qingxin);
                break;
            case 3:
                bmp = decodeResource(getResources(), R.drawable.filter_weimei);
                break;
            case 4:
                bmp = decodeResource(getResources(), R.drawable.filter_fennen);
                break;
            case 5:
                bmp = decodeResource(getResources(), R.drawable.filter_huaijiu);
                break;
            case 6:
                bmp = decodeResource(getResources(), R.drawable.filter_landiao);
                break;
            case 7:
                bmp = decodeResource(getResources(), R.drawable.filter_qingliang);
                break;
            case 8:
                bmp = decodeResource(getResources(), R.drawable.filter_rixi);
                break;
            default:
                bmp = null;
                break;
        }
        if (mBeautyChangeListener != null) {
            BeautyParams params = new BeautyParams();
            params.mFilterBmp = bmp;
            mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_FILTER);
        }
    }

    //设置绿幕
    private void setGreenScreen(int index) {
        String file = "";
        switch (index) {
            case 1:
                file = "green_1.mp4";
                break;
            default:
                break;
        }
        if (mBeautyChangeListener != null) {
            BeautyParams params = new BeautyParams();
            params.mGreenFile = file;
            mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_GREEN);
        }
    }

    //设置动效
    private void setDynamicEffect(int index) {
        String path = "";
        switch (index) {
            case 1:
                path = "assets://camera/camera_video/CameraVideoAnimal/video_rabbit";
                break;
            case 2:
                path = "assets://camera/camera_video/CameraVideoAnimal/zui";
                break;
            default:
                break;
        }
        if (mBeautyChangeListener != null) {
            BeautyParams params = new BeautyParams();
            params.mMotionTmplPath = path;
            mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_MOTION_TMPL);
        }
    }

    public void setViewVisibility(int id, int visible) {
        LinearLayout contentLayout = (LinearLayout)getChildAt(0);
        int count = contentLayout.getChildCount();
        for (int i=0; i<count; ++i) {
            View view = contentLayout.getChildAt(i);
            if (view.getId() == id) {
                view.setVisibility(visible);
                return;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.exposure_seekbar) {
            if (mBeautyChangeListener != null) {
                BeautyParams params = new BeautyParams();
                params.mExposure = ((float)progress - 10.0f)/10.0f;
                mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_EXPOSURE);
            }
        } else if (seekBar.getId() == R.id.beauty_seekbar) {
            if (mBeautyChangeListener != null) {
                BeautyParams params = new BeautyParams();
                params.mBeautyLevel = progress;
                mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_BEAUTY);
            }
        } else if (seekBar.getId() == R.id.whitening_seekbar) {
            if (mBeautyChangeListener != null) {
                BeautyParams params = new BeautyParams();
                params.mWhiteLevel = progress;
                mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_WHITE);
            }
        } else if (seekBar.getId() == R.id.bigeye_seekbar) {
            if (mBeautyChangeListener != null) {
                BeautyParams params = new BeautyParams();
                params.mBigEyeLevel = progress;
                mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_BIG_EYE);
            }
        } else if (seekBar.getId() == R.id.facelift_seekbar) {
            if (mBeautyChangeListener != null) {
                BeautyParams params = new BeautyParams();
                params.mFaceSlimLevel = progress;
                mBeautyChangeListener.onBeautyParamsChange(params, BEAUTYPARAM_FACE_LIFT);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
