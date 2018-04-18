package com.hxwl.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/3/6.
 */

public class Photos {

    public static String telRegex = "[1][3578][123456789]";

    public static String stringPhoto(String string){
        if (string.length()==15){
            String maskNumber = string.substring(0,string.length()-9)+"****"+string.substring(string.length()-5,string.length());
            return maskNumber;
        }else {
            return string;
        }
    }
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    public static String Mobile2(String number) {
        String maskNumber = number.substring(0,3)+"****"+number.substring(number.length()-4,number.length());
        return maskNumber;

    }


}
