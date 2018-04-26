package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.flyco.tablayout.SlidingTabLayout;
import com.hxwl.newwlf.home.home.follow.LeavingMessageFragment;
import com.hxwl.wlf3.home.remenhot.GenDuoActivity;
import com.hxwl.wulinfeng.MainActivity;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/*
*  赛事赛程动态
* */
public class EventActivity extends BaseActivity {
    private String scheduleid;
    public static Intent getIntent(Context context,String scheduleid){
        Intent intent=new Intent(context,EventActivity.class);
        intent.putExtra("scheduleid",scheduleid);
        return intent;
    }
    private TabLayout tab;
    private ViewPager pager;
    List<Fragment> listFragment= new ArrayList<>();
    List<String> listString = new ArrayList<>();

    private List<Integer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        scheduleid=getIntent().getStringExtra("scheduleid");
        initView();




    }

    private void initlist() {
        listString.add("赛事指南");
        listString.add("赛事动态");
        listString.add("讨论区");
    }

    private void initView() {

        tab = (TabLayout) findViewById(R.id.event_tablayout);
        pager = (ViewPager) findViewById(R.id.event_viewpager);

        initlist();
        getlist();
        tab.addTab(tab.newTab().setText(listString.get(0)));
        tab.addTab(tab.newTab().setText(listString.get(1)));
        tab.addTab(tab.newTab().setText(listString.get(2)));
        tab.setupWithViewPager(pager);

        Pageradapters adapter=new Pageradapters(getSupportFragmentManager());
        pager.setAdapter(adapter);
        LinearLayout linearLayout = (LinearLayout) tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);



    }
    public List<Fragment> getlist() {
        listFragment.add(new GuideFragment());
        listFragment.add(new DynamicFragment());
//        listFragment.add(new CommentFragment());//LeavingMessageFragment
        LeavingMessageFragment leavingMessageFragment=  new LeavingMessageFragment();
        listFragment.add(leavingMessageFragment);//LeavingMessageFragment

        leavingMessageFragment.setNewsId(scheduleid,"4");
        return listFragment;
    }




    public class Pageradapters extends FragmentPagerAdapter{
        public Pageradapters(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listString.get(position);
        }
    }

}
