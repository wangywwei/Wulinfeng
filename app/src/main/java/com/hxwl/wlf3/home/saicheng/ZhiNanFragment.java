package com.hxwl.wlf3.home.saicheng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.AppUtils;

/**
 * Created by Administrator on 2018/4/16.
 */

public class ZhiNanFragment extends BaseFragment{

private View zhinan_item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

//
//        if (zhinan_item==null){
//            zhinan_item = inflater.inflate(R.layout.zhinan_item, container, false);
////            initView();
////            initData();
//
//        }else {
//            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
//            ViewGroup parent = (ViewGroup) frag_wulin.getParent();
//            if (parent != null) {
//                parent.removeView(zhinan_item);
//            }
//        }
//        AppUtils.setTitle(getActivity());
//        return zhinan_item ;

    }
}
/*
* extends BaseFragment implements OnTabSelectListener {

    private View frag_wulin;
    private FrameLayout vp;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ItemDetailForExpandFragment mZuixin;
    private RelativeLayout rl_senddy;
    private RelativeLayout rl_sendvideo;
    private ArrayList<HotHuatiBean.DataBean> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_wulin == null)
        {
            frag_wulin = inflater.inflate(R.layout.frag_wulin, container, false);
            initView();
            initData();
        }else{
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_wulin.getParent();
            if (parent != null)
            {
                parent.removeView(frag_wulin);
            }
        }
        AppUtils.setTitle(getActivity());
        return frag_wulin ;
    }
    private HotHuatiBean bean;
    private int pageNumber=1;

    private FragmentManager fragmentManager;
    private void initData() {
        mZuixin = new ItemDetailForExpandFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.vp, mZuixin, ItemDetailForExpandFragment.class.getSimpleName()).commit();
    }

    private void initView() {
        vp = (FrameLayout)frag_wulin.findViewById(R.id.vp);
        ImageView img_photo = (ImageView) frag_wulin.findViewById(R.id.img_photo);
        ImageView img_message = (ImageView) frag_wulin.findViewById(R.id.img_message);



        img_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatService.onEvent(getActivity(),"WulinRelease","武林发布",1);
                TCAgent.onEvent(getActivity(),"WulinRelease","武林发布" );
                    Intent intent = new Intent(getActivity() ,PostedActivity.class) ;
                    intent.putExtra("type","") ;
                    startActivity(intent);

            }
        });
        img_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), MessageActivity.class);
                    startActivity(intent);

            }
        });
        rl_senddy = (RelativeLayout) frag_wulin.findViewById(R.id.rl_senddy);
        rl_senddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                    startActivity(PostedActivity.getIntent(getActivity()));
            }
        });
        rl_sendvideo = (RelativeLayout) frag_wulin.findViewById(R.id.rl_sendvideo);
        rl_sendvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                startActivity(PictureSelectorActivity.getIntent(getActivity()));
            }
        });
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }


    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "武林");
        TCAgent.onPageStart(getActivity(), "武林");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(),"武林");
        TCAgent.onPageEnd(getActivity(),"武林");
    }
}

* */