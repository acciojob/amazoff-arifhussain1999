package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    static HashMap<String,Order> orderHashMap= new HashMap<>();
    static HashMap<String,DeliveryPartner> deliveryPartnerHashMap=new HashMap<>();
    static HashMap<String,List<String>> listHashMap = new HashMap<>();
    static HashMap<String,String> assignedOrderMap = new HashMap<>();
    public static void addOrder(Order order) {
        String orderID= order.getId();
        orderHashMap.put(orderID,order);
    }

    public static void addPartner(String partnerId) {
        deliveryPartnerHashMap.put(partnerId, new DeliveryPartner(partnerId));
    }

    public static void addOrderPartnerPair(String orderId, String partnerId) {
        if(orderHashMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId))
        {
            List<String> orderList=listHashMap.getOrDefault(partnerId,new ArrayList<>());
            orderList.add(orderId);
            listHashMap.put(partnerId,orderList);
        }
        deliveryPartnerHashMap.get(partnerId).setNumberOfOrders(deliveryPartnerHashMap.get(partnerId).getNumberOfOrders()+1);
        //Now order has been assigned
        assignedOrderMap.put(orderId, partnerId);
    }

    public static Order getOrderById(String orderId) {

        return orderHashMap.get(orderId);
    }

    public static DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.get(partnerId);
    }

    public static List<String> getOrdersByPartnerId(String partnerId) {
        return listHashMap.get(partnerId);
    }

    public static List<String> getAllOrders() {

        return new ArrayList<>(orderHashMap.keySet());
    }

    public static Integer getCountOfUnassignedOrders() {
        return orderHashMap.size()-assignedOrderMap.size();
    }

    public static Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int ordersLeft=0;

        int targetHours = Integer.parseInt(time.substring(0,2));
        int targetMinutes = Integer.parseInt(time.substring(3));
        int targetTimeInMinutes=targetHours * 60 + targetMinutes;

        List<String> orderList = listHashMap.get(partnerId);
        for (String order : orderList) {
            int deliveryTimeInMinutes = orderHashMap.get(order).getDeliveryTime();
            if (deliveryTimeInMinutes > targetTimeInMinutes) {
                ordersLeft++;
            }
        }

        return ordersLeft;
    }

    public static String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orderList = listHashMap.getOrDefault(partnerId,new ArrayList<>());
        int last=Integer.MIN_VALUE;
        if (!orderList.isEmpty()) {
           for(String order : orderList)
           {
              int time =orderHashMap.get(order).getDeliveryTime();
              last = Math.max(time,last);
           }
        }
        int deliveryTimeInMinutes = last;
        int hours = deliveryTimeInMinutes / 60;
        int minutes = deliveryTimeInMinutes % 60;

        String HH = ""+hours;
        String MM = ""+minutes;

        if(HH.length()==1){
            HH = '0'+HH;
        }
        if(MM.length()==1){
            MM = '0'+MM;
        }
        return HH+':'+MM;
    }

    public static void deletePartnerById(String partnerId) {
        List<String> orders = listHashMap.get(partnerId);

        for(String order : orders)
        {
            assignedOrderMap.remove(order);
        }
        deliveryPartnerHashMap.remove(partnerId);
        listHashMap.remove(partnerId);
    }

    public static void deleteOrderById(String orderId) {

           orderHashMap.remove(orderId);
           String partnerId = assignedOrderMap.get(orderId);
           listHashMap.get(partnerId).remove(orderId);
           deliveryPartnerHashMap.get(partnerId).setNumberOfOrders(deliveryPartnerHashMap.get(partnerId).getNumberOfOrders()-1);
           assignedOrderMap.remove(orderId);
    }
    public static Integer getOrderCountByPartnerId(String partnerId) {
      return deliveryPartnerHashMap.get(partnerId).getNumberOfOrders();
    }
}
