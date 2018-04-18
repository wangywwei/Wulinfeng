package com.hxwl.wulinfeng.reciver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.VideoDetailActivity;
import com.hxwl.wulinfeng.activity.WebViewCurrencyActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/1/21.
 */
public class JGnoticeTiaozhuan {

    private static Intent intent;

    public static void doJG(String JGType, JSONObject json, Context m_Context, Bundle bundle) throws JSONException {
        try {
            switch (JGType){
                case "1"://号资讯
                    intent = new Intent(m_Context,ZiXunDetailsActivity.class);
                    intent.putExtra("id",json.getString("Id"));

                    break;
                case "2"://号图集
                    intent=new Intent(m_Context,TuJiDetailsActivity.class);
                    intent.putExtra("id",json.getString("Id"));
                    break;
                case "10"://号视频
                    intent=new Intent(m_Context,VideoDetailActivity.class);
                    intent.putExtra("id",json.getString("Id"));
                    break;
                case "3": //直播详情
                    intent=new Intent(m_Context,LiveDetailActivity.class);
                    intent.putExtra("saichengId",json.getString("saichengId"));
                    intent.putExtra("saishiId",json.getString("saishiId"));

                    break;
                case "4": //视频回顾详情
                    intent=new Intent(m_Context,HuiGuDetailActivity.class);
                    intent.putExtra("saichengId",json.getString("saichengId"));
                    intent.putExtra("saishiId",json.getString("saishiId"));
                    break;
                case "13": //网页详情
                    intent = new Intent(m_Context, WebViewCurrencyActivity.class);
                    intent.putExtra("url",json.getString("url"));
                    intent.putExtra("title", "武林风");
                    break;
                default:
                    break;

            }
            intent.putExtras(bundle);

            m_Context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
