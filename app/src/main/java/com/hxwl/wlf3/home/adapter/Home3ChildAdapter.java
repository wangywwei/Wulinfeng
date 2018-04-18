package com.hxwl.wlf3.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */
/*
* 赛程列表适配器（备用）
*
* */
public class Home3ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<Home3Bean> list;
    private OnItemClickListener clickListener;
    private final int JUDGMENT_VIEW1 = 1;
    private final int JUDGMENT_VIEW2 = 2;
    private final int JUDGMENT_VIEW3 = 3;
    private final int JUDGMENT_VIEW4 = 4;
    private final int JUDGMENT_VIEW5 = 5;
    private final int JUDGMENT_VIEW6 = 6;

    public Home3ChildAdapter(List<Home3Bean> list, OnItemClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {


        String code = list.get(position).getCode();

        if (code.endsWith("1")){
            return JUDGMENT_VIEW1;
        }else if(code.endsWith("2")){
            return JUDGMENT_VIEW2;
        }else if(code.endsWith("3")){
            return JUDGMENT_VIEW3;
        }else if(code.endsWith("4")){
            return JUDGMENT_VIEW4;
        }else if(code.endsWith("5")){
            return JUDGMENT_VIEW5;
        }else if(code.endsWith("6")){
            return JUDGMENT_VIEW6;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        /*
        *    <ImageView
           android:layout_width="@dimen/wlf_343dp"
           android:layout_height="@dimen/wlf_87dp"
            android:layout_marginTop="140dp"
           android:layout_centerHorizontal="true"
           android:src="@drawable/shijianzhou"
           />
        *
        *
        *
         View view = View.inflate(context, R.layout.item1, null);
            viewHolder = new ViewHolder(view);
        *  *
        *
        * */
        if (viewType == JUDGMENT_VIEW1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home3child1_item, parent, false);
            return new Home3ChildAdapter.ProgressViewHolder1(view);

        } else if (viewType == JUDGMENT_VIEW2) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home3child2_item, parent, false);
            return new Home3ChildAdapter.ViewHolder(view);
        } else if (viewType == JUDGMENT_VIEW3) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home3child3_item, parent, false);
            return new Home3ChildAdapter.ProgressViewHolder(view);


        } else if (viewType == JUDGMENT_VIEW4) {


            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home3child4_item, parent, false);
            return new Home3ChildAdapter.ViewHolder(view);


        } else if (viewType == JUDGMENT_VIEW5) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home3child5_item, parent, false);
            return new Home3ChildAdapter.ViewHolder(view);


        } else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home3child6_item, parent, false);
            return new Home3ChildAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, position);
            }
        });
        if(holder instanceof Home3ChildAdapter.ViewHolder){

//            赋值
//            ((Home3ChildAdapter.ViewHolder) holder).movieName.setText(list.get(position).getName());
//            ((Home3ChildAdapter.ViewHolder) holder).releaseTime.setText(list.get(position).getReleaseTime());




//            Picasso.with(context).load(list.get(position).getImageUrl()).into(((ViewHolder) holder).imageView);
//            Glide.with(context).load(list.get(position).getImageUrl()).centerCrop().into(((ViewHolder) holder).imageView);
//            ImageLoader.build(context).bindBitmap(list.get(position).getImageUrl(), ((ViewHolder) holder).imageView);
        } else if(holder instanceof Home3ChildAdapter.ProgressViewHolder){
//            Home3ChildAdapter.ProgressViewHolder progressViewHolder = (Home3ChildAdapter.ProgressViewHolder) holder;
//            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //对应布局的id
    class ProgressViewHolder1 extends RecyclerView.ViewHolder {

//        @BindView(R.id.progressBar)
//        ProgressBar progressBar;          home3child1_item
//        @BindView(R.id.textView) TextView textView;

        private ImageView imageView;

        public ProgressViewHolder1(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);


        }
    }

    /*
    *


       <ImageView
           android:id="@+id/caicheng_img"
           android:layout_width="@dimen/wlf_343dp"
           android:layout_height="@dimen/wlf_178dp"
           android:layout_alignParentTop="true"
           android:layout_centerHorizontal="true"
           android:scaleType="fitXY"
           android:src="@drawable/wlf_deimg"
         />


       <TextView
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_alignParentTop="true"
           android:layout_centerHorizontal="true"
           android:layout_marginTop="@dimen/wlf_24dp"
           android:text="武林风之环球拳王争霸赛昆明赛"
           android:textColor="#FFFFFF"
           android:textSize="@dimen/text_20sp"
           android:id="@+id/caicheng_name" />


       <TextView
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:layout_below="@+id/caicheng_name"
           android:layout_centerHorizontal="true"
           android:layout_marginTop="@dimen/wlf_2dp"
           android:text="2018.3.3/21:15"
           android:textColor="#FFFFFF"
           android:textSize="@dimen/text_14sp"
           android:id="@+id/caicheng_time" />


       <ImageView
           android:layout_marginTop="@dimen/wlf_24dp"
           android:layout_width="@dimen/wlf_94dp"
           android:layout_height="@dimen/wlf_42dp"
           android:layout_alignEnd="@+id/caicheng_time"
           android:layout_alignRight="@+id/caicheng_time"
           android:layout_below="@+id/caicheng_time"
           android:src="@drawable/yuyue3"
           android:layout_marginBottom="@dimen/wlf_11dp"
           android:id="@+id/imageView5" />

       <!--android:layout_marginTop="@dimen/wlf_140dp"-->
       <ImageView
           android:id="@+id/caicheng_shijianzhou"
           android:layout_width="@dimen/wlf_343dp"
           android:layout_height="@dimen/wlf_87dp"
            android:layout_marginTop="140dp"
           android:layout_centerHorizontal="true"
           android:src="@drawable/shijianzhou"
           />



    *
    * */


    class ViewHolder extends RecyclerView.ViewHolder{

//        @BindView(R.id.movie_name)
//        TextView movieName;
//        @BindView(R.id.release_time) TextView releaseTime;

        public ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

    //对应布局的id
    class ProgressViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.progressBar)
//        ProgressBar progressBar;          home3child1_item
//        @BindView(R.id.textView) TextView textView;

        public ProgressViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);

        }
    }


}

