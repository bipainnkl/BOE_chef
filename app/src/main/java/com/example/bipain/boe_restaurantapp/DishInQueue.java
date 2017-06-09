package com.example.bipain.boe_restaurantapp;

/**
 * Created by BiPain on 6/5/2017.
 */

public class DishInQueue {
    private Dish dish;
    private int orderId;

    public DishInQueue(Dish dish, int orderId) {
        this.dish = dish;
        this.orderId = orderId;
    }

    public DishInQueue() {
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
