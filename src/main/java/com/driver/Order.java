package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order() {
    }

    public Order(String id, String deliveryTime) {
       this.id=id;


        int targetHours = Integer.parseInt(deliveryTime.substring(0,2));
        int targetMinutes = Integer.parseInt(deliveryTime.substring(3));
        this.deliveryTime=targetHours * 60 + targetMinutes;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
