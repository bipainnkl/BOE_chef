package com.example.bipain.boe_restaurantapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.bipain.boe_restaurantapp.R;
import com.example.bipain.boe_restaurantapp.activities.WaiterActivity;
import com.example.bipain.boe_restaurantapp.model.WaiterNotification;
import com.example.bipain.boe_restaurantapp.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hoang on 10/06/2017.
 */

public class DishServeAdatper extends RecyclerView.Adapter<DishServeAdatper.ViewHolder> {
    List<WaiterNotification> waiterNotifications;
    private Context mContext;

    public DishServeAdatper() {
        waiterNotifications = new ArrayList<>();
    }

    public void setData(List<WaiterNotification> list) {
        this.waiterNotifications = list;
        notifyDataSetChanged();
    }

    public void addData(WaiterNotification activity) {
        int pos = waiterNotifications.size();
        waiterNotifications.add(activity);
        notifyItemRangeChanged(pos, waiterNotifications.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_dish_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WaiterNotification item = waiterNotifications.get(position);
        holder.tvDishNam.setText(item.getDish().getDishName());
        holder.tvTableNumber.setText("Table No." + String.valueOf(item.getTableNumber()));
        if (Constant.OVER_TIME == item.getType() && !item.isNotify()) {
//            for (int i = 0; i < 4; i++) {
//                holder.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWarning));
//                try {
//                    Thread.sleep(400);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                holder.container.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
//                try {
//                    Thread.sleep(400);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            holder.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWarning));
            item.setNotified();
        } else if(item.isNotify()){
            holder.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWarning));
        }
    }

    @Override
    public int getItemCount() {
        return waiterNotifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDishName)
        TextView tvDishNam;
        @BindView(R.id.tvTableNumber)
        TextView tvTableNumber;
        @BindView(R.id.tvServe)
        TextView tvServe;
        @BindView(R.id.container)
        RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initView();
        }

        private void initView() {
            tvServe.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                waiterNotifications.remove(pos);
                notifyItemRemoved(pos);
                listner.onServeClick();
            });
        }
    }

    public void notifyLongTime(WaiterNotification notification) {
        int pos = waiterNotifications.indexOf(notification);
        notification.setOverTime();
        notifyItemChanged(pos);
    }

    public interface WaiterListner {
        void onServeClick();
    }

    private WaiterListner listner;

    public void setListner(WaiterListner listner) {
        this.listner = listner;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (null != recyclerView) {
            mContext = recyclerView.getContext();
        }
        super.onAttachedToRecyclerView(recyclerView);
    }
}
