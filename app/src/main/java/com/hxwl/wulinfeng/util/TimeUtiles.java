package com.hxwl.wulinfeng.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class TimeUtiles {
    public static String getTimes(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
    public static String getTimeForDuizhen(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }


    public static String getTimeed(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM/dd");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * 获取不同位置的数据
     * @param time
     * @param flag m 月 d 日
     * @return
     */
    public static String getTimeedForMOrD(String time,String flag) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM/dd");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] timeSp = times.split("/");
        if("m".equals(flag)){
            return timeSp[0];
        }else if("d".equals(flag)){
            return timeSp[1];
        }else{
            return times;
        }
    }
    public static String getTimeed_(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
    public static String getTimeedForMyMartial(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy/MM/dd");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
    public static String getTimeeForTuiJ(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH:mm");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }
    public static String getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t*1000);
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }
    /**
     * 并用分割符把时间分成时间数组
     *
     * @param time
     * @return
     */
    public static String[] timestamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] fenge = times.split("[年月日时分秒]");
        return fenge;
    }
    //获取当前时间
    public static Date getCurrentDate(){
//        DateFormat df = new SimpleDateFormat("HH:mm:ss");
//        String format = df.format(new Date());
        return new Date();
    }
    //获取两个日期间的天数 2016-09-24 19:30
    public static int getGapCount(Date startDate, Date endDate) {

        Calendar fromCalendar = Calendar.getInstance();

        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, startDate.getHours());
        fromCalendar.set(Calendar.MINUTE, startDate.getMinutes());
        fromCalendar.set(Calendar.SECOND, startDate.getSeconds());
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, endDate.getHours());
        toCalendar.set(Calendar.MINUTE, endDate.getMinutes());
        toCalendar.set(Calendar.SECOND, endDate.getSeconds());
        toCalendar.set(Calendar.MILLISECOND, 0);


        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }
    public static List<String> getGapCount1(Date startDate, Date endDate) {
        ArrayList<String> list=new ArrayList<>();
        Calendar fromCalendar = Calendar.getInstance();

        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, startDate.getHours());
        fromCalendar.set(Calendar.MINUTE, startDate.getMinutes());
        fromCalendar.set(Calendar.SECOND, startDate.getSeconds());
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, endDate.getHours());
        toCalendar.set(Calendar.MINUTE, endDate.getMinutes());
        toCalendar.set(Calendar.SECOND, endDate.getSeconds());
        toCalendar.set(Calendar.MILLISECOND, 0);
        int secs=(int)(toCalendar.getTime().getTime() - fromCalendar.getTime().getTime())/ 1000;
        int day = secs / (60 * 60 * 24);
        int hour = (secs % (60 * 60 * 24)) / (60 * 60);
        int minute = ((secs % (60 * 60 * 24)) % (60 * 60))/60;
        //int second = ((secs % (60 * 60 * 24)) % (60 * 60)) % 60;
        list.add(day+"");
        list.add(hour+"");
        list.add(minute+"");
       // list.add(second);
        return list;
       // return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }
    public static Long getGapCount2(Date startDate, Date endDate) {

        Calendar fromCalendar = Calendar.getInstance();

        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, startDate.getHours());
        fromCalendar.set(Calendar.MINUTE, startDate.getMinutes());
        fromCalendar.set(Calendar.SECOND, startDate.getSeconds());
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, endDate.getHours());
        toCalendar.set(Calendar.MINUTE, endDate.getMinutes());
        toCalendar.set(Calendar.SECOND, endDate.getSeconds());
        toCalendar.set(Calendar.MILLISECOND, 0);


        return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime());
    }
    //string 转 Data
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    //string 转 Data
    public static Date stringToDate(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    public static Date stringToDate(long strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = new Date(strTime);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
    public static long dateToMillis(String date) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long millionSeconds = 0;
        try {
            millionSeconds = sdf.parse(date).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millionSeconds;
    }

    //比较大小
    public static String DateCompare(String old, String now){
        java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Calendar c1=java.util.Calendar.getInstance();
        java.util.Calendar c2=java.util.Calendar.getInstance();
        try {
            c1.setTime(df.parse(old));
            c2.setTime(df.parse(now));
        } catch (Exception e) {
            System.out.println("格式不正确");
            e.printStackTrace();
        }
        int result=c1.compareTo(c2);
        if(result==0){
            return "相等";
        }else if(result<0){
            return "小于";
        }else
            return "大于";
    }
    public static String getCurrenttime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String timestamp = formatter.format(curDate);

        return timestamp;
    }

}
