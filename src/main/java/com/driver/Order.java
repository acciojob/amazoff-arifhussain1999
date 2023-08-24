package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order() {
    }

    public Order(String id, String deliveryTime) {
       this.id=id;

        String[] timeParts = deliveryTime.split(":");
        int targetHours = Integer.parseInt(timeParts[0]);
        int targetMinutes = Integer.parseInt(timeParts[1]);
        this.deliveryTime=targetHours * 60 + targetMinutes;
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
