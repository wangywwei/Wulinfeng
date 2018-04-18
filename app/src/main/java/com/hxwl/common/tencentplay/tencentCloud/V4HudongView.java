package com.hxwl.common.tencentplay.tencentCloud;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.ImageUtils;


/**
 * Created by Administrator on 2017/3/9.
 *
 * 中间互动的浮层
 *
 */

public class V4HudongView extends LinearLayout {
    private Context context;
    private ImageView bule_pic1;
    private ImageView bule_pic2;
    private ImageView bule_pic3;
    private ImageView red_pic1;
    private ImageView red_pic2;
    private ImageView red_pic3;
    private ImageView blue_icon;
    private ImageView red_icon;
    private TextView tv_bule_hudongNum;
    private TextView tv_red_hudongNum;
    private int rightCountOne;
    private  int rightCountTwo;
    private  int rightCountThree;
    private  int leftCountOne;
    private  int leftCountTwo;
    private  int leftCountThree;


    public V4HudongView(Context context) {//new的时候调用
        this(context,null);
    }

    public V4HudongView(Context context, AttributeSet attrs) { //布局文件的时候用

        this(context, attrs,0);
    }

    public V4HudongView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        context = getContext();
        initView(context);
        initData();
    }

    private void initData() {
//        bule_pic1.setOnClickListener(this);
//        bule_pic2.setOnClickListener(this);
//        bule_pic3.setOnClickListener(this);
//        red_pic1.setOnClickListener(this);
//        red_pic2.setOnClickListener(this);
//        red_pic3.setOnClickListener(this);
    }



    private void initView(Context context) {
        bule_pic1 = (ImageView) findViewById(R.id.blue_pic1);
        bule_pic2 = (ImageView) findViewById(R.id.blue_pic2);
        bule_pic3 = (ImageView) findViewById(R.id.blue_pic3);
        red_pic1 = (ImageView) findViewById(R.id.red_pic1);
        red_pic2 = (ImageView) findViewById(R.id.red_pic2);
        red_pic3 = (ImageView) findViewById(R.id.red_pic3);

        blue_icon = (ImageView) findViewById(R.id.blue_icon);
        red_icon = (ImageView) findViewById(R.id.red_icon);

        tv_bule_hudongNum = (TextView) findViewById(R.id.tv_blue_hudongNum);
        tv_red_hudongNum = (TextView) findViewById(R.id.tv_red_hudongNum);
    }
//    @Override
//    public void onClick(View v) {
//        try {
//            switch (v.getId()){
//                case R.id.red_pic1:
//                    TankingDataUtils.eventClick(context, StatEventName.red_hudong,"LiveDetail_FullScreen_hudongon_red");
////                    leftCountOne++;
//                    if (getRedHudongNum()!=null){
//                        if ( !getRedHudongNum().equals("10W+")){
//                            if (Integer.parseInt(getRedHudongNum())==99999){
//                                setRedHudongNum("10W+");
//                            }else{
//                                setRedHudongNum(Integer.parseInt(getRedHudongNum())+1+"");
//                            }
//
//                        }else{
//                            setRedHudongNum("10W+");
//                        }
//                    }else{
//                        setRedHudongNum(0+"");
//                    }
//
//                    if(onMyClicklistener!=null){
//                        onMyClicklistener.onClicklistenerLeftOne(v);
//                    }
//                    break;
//                case R.id.red_pic2:
//                    TankingDataUtils.eventClick(context, StatEventName.red_hudong,"LiveDetail_FullScreen_hudongon_red");
////                    leftCountTwo++;
//                    if (getRedHudongNum()!=null){
//                        if ( !getRedHudongNum().equals("10W+")){
//                            if (Integer.parseInt(getRedHudongNum())==99999){
//                                setRedHudongNum("10W+");
//                            }else{
//                                setRedHudongNum(Integer.parseInt(getRedHudongNum())+1+"");
//                            }
//
//                        }else{
//                            setRedHudongNum("10W+");
//                        }
//                    }else{
//                        setRedHudongNum(0+"");
//                    }
//
//
//                    if(onMyClicklistener!=null){
//                        onMyClicklistener.onClicklistenerLeftTwo(v);
//                    }
//                    break;
//                case R.id.red_pic3:
//                    TankingDataUtils.eventClick(context, StatEventName.red_hudong,"LiveDetail_FullScreen_hudongon_red");
////                    leftCountThree++;
//
//                    if (getRedHudongNum()!=null){
//                        if ( !getRedHudongNum().equals("10W+")){
//                            if (Integer.parseInt(getRedHudongNum())==99999){
//                                setRedHudongNum("10W+");
//                            }else{
//                                setRedHudongNum(Integer.parseInt(getRedHudongNum())+1+"");
//                            }
//
//                        }else{
//                            setRedHudongNum("10W+");
//                        }
//                    }else{
//                        setRedHudongNum(0+"");
//                    }
//                    if(onMyClicklistener!=null){
//                        onMyClicklistener.onClicklistenerLeftThree(v);
//                    }
//                    break;
//                case R.id.blue_pic1:
//                    TankingDataUtils.eventClick(context, StatEventName.blue_hudong,"LiveDetail_FullScreen_hudongon_blue");
////                    rightCountOne++;
//                    if (getBlueHudongNum()!=null){
//                        if ( !getBlueHudongNum().equals("10W+")){
//                            if (Integer.parseInt(getBlueHudongNum())==99999){
//                                setBlueHudongNum("10W+");
//                            }else{
//                                setBlueHudongNum(Integer.parseInt(getBlueHudongNum())+1+"");
//                            }
//
//                        }else{
//                            setBlueHudongNum("10W+");
//                        }
//                    }else{
//                        setBlueHudongNum(0+"");
//                    }
//
//                    if(onMyClicklistener!=null){
//                        onMyClicklistener.onClicklistenerRightOne(v);
//                    }
//                    break;
//                case R.id.blue_pic2:
//                    TankingDataUtils.eventClick(context, StatEventName.blue_hudong,"LiveDetail_FullScreen_hudongon_blue");
////                    rightCountTwo++;
//                    if (getBlueHudongNum()!=null){
//                        if ( !getBlueHudongNum().equals("10W+")){
//                            if (Integer.parseInt(getBlueHudongNum())==99999){
//                                setBlueHudongNum("10W+");
//                            }else{
//                                setBlueHudongNum(Integer.parseInt(getBlueHudongNum())+1+"");
//                            }
//
//                        }else{
//                            setBlueHudongNum("10W+");
//                        }
//                    }else{
//                        setBlueHudongNum(0+"");
//                    }
//                    if(onMyClicklistener!=null){
//                        onMyClicklistener.onClicklistenerRightTwo(v);
//                    }
//                    break;
//                case R.id.blue_pic3:
//                    TankingDataUtils.eventClick(context, StatEventName.blue_hudong,"LiveDetail_FullScreen_hudongon_blue");
////                    rightCountThree++;
//                    if (getBlueHudongNum()!=null){
//                        if ( !getBlueHudongNum().equals("10W+")){
//                            if (Integer.parseInt(getBlueHudongNum())==99999){
//                                setBlueHudongNum("10W+");
//                            }else{
//                                setBlueHudongNum(Integer.parseInt(getBlueHudongNum())+1+"");
//                            }
//
//                        }else{
//                            setBlueHudongNum("10W+");
//                        }
//                    }else{
//                        setBlueHudongNum(0+"");
//                    }
//
//                    if(onMyClicklistener!=null){
//                        onMyClicklistener.onClicklistenerRightThree(v);
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }


    //设置蓝方互动数
    public void setBlueHudongNum(String num){
        if (num!=null){
            tv_bule_hudongNum.setText(num+"");
        }
    }
    //设置红方互动数
    public void setRedHudongNum(String num){
        if (num!=null){
            tv_red_hudongNum.setText(num+"");
        }
    }

    //得到蓝方互动数
    public String  getBlueHudongNum(){
        return tv_bule_hudongNum.getText().toString().trim();
    }
    //得到红方互动数
    public String getRedHudongNum(){
        return tv_red_hudongNum.getText().toString().trim();
    }

    //设置蓝方头像
    public void setBlueImg(String str){
        if (str!=null){
            ImageUtils.getCirclePic(str,blue_icon,context);
        }
    }

    //设置红方头像
    public void setRedImg(String str){
        if (str!=null){
            ImageUtils.getCirclePic(str,red_icon,context);
        }
    }

    //设置默认头像
    public void setDefultImg(){
        blue_icon.setImageResource(R.drawable.touxiang1);
        red_icon.setImageResource(R.drawable.touxiang1);
    }


    private void setLeftCount(int leftCountOne,int leftCountTwo, int leftCountThree) {
        this.leftCountOne = leftCountOne;
        this.leftCountTwo = leftCountTwo;
        this.leftCountThree = leftCountThree;
    }


    public void setRightCount(int rightCountOne,int rightCountTwo,int rightCountThree) {
        this.rightCountOne = rightCountOne;
        this.rightCountTwo = rightCountTwo;
        this.rightCountThree = rightCountThree;
    }




    public void setRightCountOne(int rightCountOne) {
        this.rightCountOne=rightCountOne;
    }

    public void setRightCountTwo(int rightCountTwo) {
        this.rightCountTwo=rightCountTwo;
    }
    public void setRightCountThree(int rightCountThree) {
        this.rightCountThree=rightCountThree;
    }

    public void setLeftCountOne(int leftCountOne) {
        this.leftCountOne=leftCountOne;
    }

    public void setLeftCountTwo(int leftCountTwo) {
        this.leftCountTwo=leftCountTwo;
    }
    public void setLeftCountThree(int leftCountThree) {
        this.leftCountThree=leftCountThree;
    }





    public int getRightCountOne() {
        return rightCountOne;
    }

    public int getRightCountTwo() {
        return rightCountTwo;
    }

    public int getRightCountThree() {
        return rightCountThree;
    }

    public int getLeftCountOne() {
        return leftCountOne;
    }

    public int getLeftCountTwo() {
        return leftCountTwo;
    }

    public int getLeftCountThree() {
        return leftCountThree;
    }

    public interface OnMyClicklistener{
        void onClicklistenerLeftOne(View view);
        void onClicklistenerLeftTwo(View view);
        void onClicklistenerLeftThree(View view);
        void onClicklistenerRightOne(View view);
        void onClicklistenerRightTwo(View view);
        void onClicklistenerRightThree(View view);
    }
    public OnMyClicklistener onMyClicklistener;
    public void setOnMyClickListener(OnMyClicklistener onMyClicklistener){
        this.onMyClicklistener = onMyClicklistener;
    }
}
