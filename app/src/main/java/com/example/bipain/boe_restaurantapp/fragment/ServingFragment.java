package com.example.bipain.boe_restaurantapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bipain.boe_restaurantapp.R;
import com.example.bipain.boe_restaurantapp.activities.SchedulerThread;
import com.example.bipain.boe_restaurantapp.activities.WaiterActivity;
import com.example.bipain.boe_restaurantapp.adapter.DishServeAdatper;
import com.example.bipain.boe_restaurantapp.model.DishNotification;
import com.example.bipain.boe_restaurantapp.model.WaiterNotification;
import com.example.bipain.boe_restaurantapp.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServingFragment extends Fragment {

    private final static String GCM_TOKEN = "GCM_TOKEN";
    private Unbinder unbinder;
    private Handler myHandler;

    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.rvDish)
    RecyclerView rvDish;
    DishServeAdatper mAdapter;

    public ServingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new DishServeAdatper();
        mAdapter.setListner(() -> setTotal());
        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                WaiterNotification i = (WaiterNotification) msg.obj;
                if (i.isNotifyToWarning()) {
                    mAdapter.removeData(i);
                } else {
                    mAdapter.notifyLongTime(i);
                }
                setTotal();
//                Log.d(LOG_TAG, i.getDish().getDishName()
//                        + "-"
//                        + String.valueOf(i.getDish().getDishId()));
            }
        };

    }

    public Handler getMyHandler() {
        return myHandler;
    }

    private void setUpRecyclerView() {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        rvDish.setLayoutManager(manager);
        rvDish.addItemDecoration(decoration);
        rvDish.setAdapter(mAdapter);
        rvDish.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int a = ((LinearLayoutManager)manager).findFirstVisibleItemPosition();
                ((LinearLayoutManager)manager).findLastVisibleItemPosition();
            }
        });

        setTotal();

        scheduler = PublishSubject.create();
        scheduler
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(endPoint)
                .subscribe(id -> ToastUtils.toastShortMassage(getContext(), "dmmmmmm"));
    }

    private void setTotal() {
        tvTotal.setText("Tổng số: " + String.valueOf(mAdapter.getItemCount()) + " dĩa");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        setTotal();
//        for (WaiterNotification n : fakeData()) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            addDataHandlerAndAdapter(n);
//        }
    }

    public void addDataHandlerAndAdapter(WaiterNotification notification) {
        notification.initCountTime();
        mAdapter.addData(notification);
        getSchduler().addItem(notification);
        setTotal();
    }

    private SchedulerThread getSchduler() {
        return ((WaiterActivity) getActivity()).myScheduler;
    }

    private PublishSubject<Integer> scheduler;

    private PublishSubject<ServingFragment> endPoint = PublishSubject.create();

    @Override
    public void onStop() {
        super.onStop();
        endPoint.onNext(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private List<WaiterNotification> fakeData() {
        List<WaiterNotification> notifications = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DishNotification dishNotification = new DishNotification(i, "acp colum");
            WaiterNotification waiterNotification = new WaiterNotification(4, dishNotification);
            notifications.add(waiterNotification);
        }
        return notifications;
    }
}