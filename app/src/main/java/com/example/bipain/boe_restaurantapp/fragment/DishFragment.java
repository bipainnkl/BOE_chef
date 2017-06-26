package com.example.bipain.boe_restaurantapp.fragment;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bipain.boe_restaurantapp.DishInOrder;
import com.example.bipain.boe_restaurantapp.DishInQueue;
import com.example.bipain.boe_restaurantapp.DishQueueAdapter;
import com.example.bipain.boe_restaurantapp.R;
import com.example.bipain.boe_restaurantapp.activities.TabManagerActivity;
import com.example.bipain.boe_restaurantapp.model.StatusResponse;
import com.example.bipain.boe_restaurantapp.services.Services;
import com.example.bipain.boe_restaurantapp.utils.Constant;
import com.example.bipain.boe_restaurantapp.utils.ToastUtils;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishFragment extends Fragment {

    private ListView lvDishInQueue;
    private DishQueueAdapter dishQueueAdapter;
    private LinkedList<DishInQueue> queueDish;
    private ArrayList<DishInOrder> dishInOrders;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queueDish = new LinkedList<>();
        dishInOrders = new ArrayList<>();
        setDishInOrders();
        dishQueueAdapter = new DishQueueAdapter(getActivity(), dishInOrders);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_dish_fragment, container, false);

        lvDishInQueue = (ListView) view.findViewById(R.id.lvQueueDish);
        lvDishInQueue.setAdapter(dishQueueAdapter);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dishQueueAdapter.setListener(dishId -> {
            Services services = getService();
            int orderId = getOrderIdByDish(dishId);
            services.markDishDone(orderId, dishId).enqueue(new Callback<StatusResponse>() {
                @Override
                public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                    if (response.isSuccessful()) {
                        if (null != response.body()) {
                            removeDishIsCooked(dishId);
                        }
                    }
                }

                @Override
                public void onFailure(Call<StatusResponse> call, Throwable t) {

                }
            });
        });
    }

    public void setDishInOrders() {
        if (null != queueDish && queueDish.size() > 0) {
            for (DishInQueue dishInQueue : queueDish) {
                int quantity = 0;
                DishInOrder dishInOrder = new DishInOrder();
                dishInOrder.setDish(dishInQueue.getDish());
                boolean isExited = false;
                for (DishInOrder dish : dishInOrders) {
                    if (dish.getDish().getDishId() == dishInOrder.getDish().getDishId()) {
                        isExited = true;
                        break;
                    }
                }
                if (!isExited) {
                    for (DishInQueue dish : queueDish) {
                        if (dish.getDish().getDishId() == dishInOrder.getDish().getDishId()) {
                            quantity += 1;
                        }
                    }
                    dishInOrder.setQuantity(quantity);
                    dishInOrders.add(dishInOrder);
                }
            }
        }
    }

    public void refreshListViewDish() {
        dishInOrders.clear();
        setDishInOrders();
//        lvDishInQueue.setAdapter(dishQueueAdapter);
        dishQueueAdapter.setData(dishInOrders);
        dishQueueAdapter.notifyDataSetChanged();
//        lvDishInQueue.invalidateViews();
    }

    private int getOrderIdByDish(int dishId) {
        for (DishInQueue dishInQueue : queueDish) {
            if (dishInQueue.getDish().getDishId() == dishId) {
                return dishInQueue.getOrderId();
            }
        }
        return 0;
    }

    public void removeDishIsCooked(int dishId) {
        for (DishInQueue dishInQueue : queueDish) {
            if (dishInQueue.getDish().getDishId() == dishId) {
                queueDish.remove(dishInQueue);
                break;
            }
        }
        refreshListViewDish();
    }

    public void addNewQueue(DishInQueue queue) {
        queueDish.add(queue);
        refreshListViewDish();
    }

    public Services getService() {
        return ((TabManagerActivity) getActivity()).getServices();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constant.LOG_TAG, "Dish-onstart");
        setPos();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Constant.LOG_TAG, "Dish-onResume");
    }

    private void setPos() {
        ((TabManagerActivity) getActivity()).setFragmentPos(2);
    }

    public void onKeySearchChange(String term) {
        if (null != dishQueueAdapter) {
            dishQueueAdapter.getFilter().filter(term);
        }
    }
}
