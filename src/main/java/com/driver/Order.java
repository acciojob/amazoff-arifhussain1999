package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order() {
    }

    public Order(String id, String deliveryTime) {
        int deliveryTimeInt = Integer.parseInt(deliveryTime.substring(0,2))*60 + Integer.parseInt(deliveryTime.substring(3));

        this.id = id;
        this.deliveryTime = deliveryTimeInt;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
