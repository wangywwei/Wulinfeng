package com.hxwl.wlf3.home.xiangqing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.wlf3.bean3.Pinlin3Bean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.OnClickEventUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class ShijianAdapter extends RecyclerView.Adapter<ShijianAdapter.ViewHolder> implements ShijianZIAdapter.OnZanClicklistener, ShijianZIAdapter.OnZanClicklistener2 {
    private Context context;
    private ArrayList<Pinlin3Bean.DataBean> list;
    private ShijianZIAdapter adapter;
    ArrayList<Pinlin3Bean.DataBean.CommentListBean> listBeans = new ArrayList<>();

    public ShijianAdapter(Context context, ArrayList<Pinlin3Bean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shijian_pinlun, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        GlidUtils.setGrid2(context, URLS.IMG + list.get(position).getHeadImg(), holder.pinlun_touxiang);

        if (list.get(position).getIsFavour() == 1) {
            holder.zan.setImageResource(R.drawable.yizan_icon);
        } else {
            holder.zan.setImageResource(R.drawable.zan_icon);
        }

        holder.pinlun_name.setText(list.get(position).getNickName());
        holder.pinlun_content.setText(list.get(position).getContent());
        holder.pinlun_time.setText(list.get(position).getCommentTime());
        holder.pinlun_zannum.setText(list.get(position).getFavourNum() + "");


        if (list.get(position).getCommentList() != null && list.get(position).getCommentList().size() > 0) {
            if (list.get(position).getCommentList().size() >= 3) {
                holder.pinlun_quanbu.setVisibility(View.VISIBLE);
            } else {
                holder.pinlun_quanbu.setVisibility(View.GONE);
            }
            listBeans.clear();
            listBeans.addAll(list.get(position).getCommentList());
            adapter = new ShijianZIAdapter(context, listBeans, position);
            holder.list_item.setAdapter(adapter);
            adapter.setOnPinlun1Clicklistener(this);
            adapter.setOnPinlun2Clicklistener(this);
        }


        final int dianzannum = list.get(position).getIsFavour();
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isBlank(MakerApplication.instance.getMobile())) {
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                if (OnClickEventUtils.isFastClick()) {
                    return;
                }
                if (list.get(position).getIsFavour() == 1) {
                    quxiaozan(holder.zan, list.get(position));
                } else {
                    dianzan(holder.zan, list.get(position));
                }
            }

            public void quxiaozan(final ImageView zan, final Pinlin3Bean.DataBean dataBean) {
                OkHttpUtils.post()
                        .url(URLS.CANCELFAVOUR)
                        .addParams("targetId", dataBean.getId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId", MakerApplication.instance().getUserid())
                        .addParams("type", "5")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    dataBean.setIsFavour(2);
                                    zan.setImageResource(R.drawable.zan_icon);
                                    if (dianzannum == 1) {
                                        holder.pinlun_zannum.setText(dataBean.getFavourNum() - 1 + "");//点赞数量
                                    } else {
                                        holder.pinlun_zannum.setText(dataBean.getFavourNum() + "");//点赞数量
                                    }

                                } else if (bean.getCode().equals("2002") || bean.getCode().equals("2003")) {
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));

                                } else {
                                    UIUtils.showToast(bean.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }

            public void dianzan(final ImageView zan, final Pinlin3Bean.DataBean dataBean) {
                OkHttpUtils.post()
                        .url(URLS.ADDFAVOUR)
                        .addParams("targetId", dataBean.getId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId", MakerApplication.instance().getUserid())
                        .addParams("type", "5")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    dataBean.setIsFavour(1);
                                    zan.setImageResource(R.drawable.yizan_icon);
                                    if (dianzannum == 1) {
                                        holder.pinlun_zannum.setText(dataBean.getFavourNum() + "");//点赞数量
                                    } else {
                                        holder.pinlun_zannum.setText(dataBean.getFavourNum() + 1 + "");//点赞数量
                                    }

                                } else if (bean.getCode().equals("2002") || bean.getCode().equals("2003")) {
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));

                                } else {
                                    UIUtils.showToast(bean.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });

        holder.pinlun_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onshijianClicklistener.onshijianClicklistener(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
        //点击评论的评论前面
        onPinlun1Clicklistener.onPinlun1Clicklistener(groupPosition,childPosition);

    }

    @Override
    public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
        //点击评论的评论后面
        onPinlun2Clicklistener.onPinlun2Clicklistener(groupPosition,childPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pinlun_touxiang;
        private TextView pinlun_name;
        private TextView pinlun_time;
        private TextView pinlun_text;
        private TextView pinlun_zannum;
        private ImageView zan;
        private TextView pinlun_content;
        private MyListView list_item;
        private TextView pinlun_quanbu;

        public ViewHolder(View itemView) {
            super(itemView);
            pinlun_touxiang = (ImageView) itemView.findViewById(R.id.pinlun_touxiang);
            zan = (ImageView) itemView.findViewById(R.id.zan);
            pinlun_name = (TextView) itemView.findViewById(R.id.pinlun_name);
            pinlun_time = (TextView) itemView.findViewById(R.id.pinlun_time);
            pinlun_text = (TextView) itemView.findViewById(R.id.pinlun_text);
            pinlun_zannum = (TextView) itemView.findViewById(R.id.pinlun_zannum);
            pinlun_content = (TextView) itemView.findViewById(R.id.pinlun_content);
            list_item = (MyListView) itemView.findViewById(R.id.list_item);
            pinlun_quanbu = (TextView) itemView.findViewById(R.id.pinlun_quanbu);
        }
    }

    public OnshijianClicklistener onshijianClicklistener;


    public void setOnshijianClicklistener(OnshijianClicklistener onshijianClicklistener) {
        this.onshijianClicklistener = onshijianClicklistener;
    }

    public interface OnshijianClicklistener {
        void onshijianClicklistener(int position);

    }


    public OnZanClicklistener onPinlun1Clicklistener;
    public OnZanClicklistener2 onPinlun2Clicklistener;

    public void setOnPinlun1Clicklistener(OnZanClicklistener onPinlun1Clicklistener) {
        this.onPinlun1Clicklistener = onPinlun1Clicklistener;
    }

    public void setOnPinlun2Clicklistener(OnZanClicklistener2 onPinlun2Clicklistener) {
        this.onPinlun2Clicklistener = onPinlun2Clicklistener;
    }

    public interface OnZanClicklistener {
        void onPinlun1Clicklistener(int groupPosition, int childPosition);

    }
    public interface OnZanClicklistener2 {
        void onPinlun2Clicklistener(int groupPosition, int childPosition);

    }


}
