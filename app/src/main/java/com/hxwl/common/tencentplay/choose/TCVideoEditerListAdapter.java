package com.hxwl.common.tencentplay.choose;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxwl.common.tencentplay.utils.TCUtils;
import com.hxwl.wulinfeng.R;

import java.io.File;
import java.util.ArrayList;

public class TCVideoEditerListAdapter extends RecyclerView.Adapter<TCVideoEditerListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<TCVideoFileInfo> data = new ArrayList<TCVideoFileInfo>();
    private int mLastSelected = -1;
    private boolean mMultiplePick;
    private View.OnClickListener onClickCamera ;

    public TCVideoEditerListAdapter(Context context ,View.OnClickListener onClickCamera) {
        mContext = context;
        this.onClickCamera = onClickCamera ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_ugc_video, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TCVideoFileInfo fileInfo = data.get(position);
        if(fileInfo.getFileId() == -1){//拍摄按钮
            holder.ivSelected.setVisibility(View.GONE);
            holder.duration.setVisibility(View.GONE);

            holder.thumb.setVisibility(View.GONE);
            holder.rl_paishe.setVisibility(View.VISIBLE);

            holder.rl_paishe.setOnClickListener(onClickCamera);
            holder.frmQueue.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{

            holder.rl_paishe.setVisibility(View.GONE);
            holder.thumb.setVisibility(View.VISIBLE);

            holder.frmQueue.setBackgroundColor(mContext.getResources().getColor(R.color.black));
            holder.ivSelected.setVisibility(fileInfo.isSelected() ? View.VISIBLE : View.GONE);
            holder.duration.setText(TCUtils.formattedTime(fileInfo.getDuration() / 1000));
            Glide.with(mContext).load(Uri.fromFile(new File(fileInfo.getFilePath()))).dontAnimate().into(holder.thumb);
            holder.thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMultiplePick)
                        changeMultiSelection(position);
                    else {
                        changeSingleSelection(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    public void setMultiplePick(boolean multiplePick) {
        mMultiplePick = multiplePick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumb;
        private final TextView duration;
        private final ImageView ivSelected;
        private final RelativeLayout rl_paishe;
        private final FrameLayout frmQueue;

        public ViewHolder(final View itemView) {
            super(itemView);
            thumb = (ImageView) itemView.findViewById(R.id.iv_thumb);
            duration = (TextView) itemView.findViewById(R.id.tv_duration);
            ivSelected = (ImageView) itemView.findViewById(R.id.iv_selected);
            rl_paishe = (RelativeLayout) itemView.findViewById(R.id.rl_paishe);
            frmQueue = (FrameLayout) itemView.findViewById(R.id.frmQueue);
        }
    }

    public ArrayList<TCVideoFileInfo> getMultiSelected() {
        ArrayList<TCVideoFileInfo> infos = new ArrayList<TCVideoFileInfo>();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                infos.add(data.get(i));
            }
        }
        return infos;
    }

    public TCVideoFileInfo getSingleSelected() {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                return data.get(i);
            }
        }
        return null;
    }

    public void addAll(ArrayList<TCVideoFileInfo> files ,boolean isHavaCamera) {
        this.data.clear();
        //添加拍摄假数据
        if(isHavaCamera){
            TCVideoFileInfo cameraInfo = new TCVideoFileInfo();
            cameraInfo.setFileId(-1);
            cameraInfo.setSelected(false) ;
            cameraInfo.setFileName("拍摄");
            this.data.add(cameraInfo) ;
        }
        try {
            this.data.addAll(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public void clearAll(){
        try{
            this.data.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    public void changeSingleSelection(int position) {
        if (mLastSelected != -1) {
            data.get(mLastSelected).setSelected(false);
        }
        notifyItemChanged(mLastSelected);

        TCVideoFileInfo info = data.get(position);
        info.setSelected(true);
        notifyItemChanged(position);

        mLastSelected = position;
    }

    public void changeMultiSelection(int position) {
        if (data.get(position).isSelected()) {
            data.get(position).setSelected(false);
        } else {
            data.get(position).setSelected(true);
        }
        notifyItemChanged(position);
    }

    public void  resetSelectItem(){
        try{
            for(int i=0;i<data.size();i++){
                if(data.get(i).isSelected()){
                    data.get(i).setSelected(false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

}
