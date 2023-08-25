package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    static HashMap<String,Order> orderHashMap= new HashMap<>();
    static HashMap<String,DeliveryPartner> deliveryPartnerHashMap=new HashMap<>();
    static HashMap<String,List<Order>> listHashMap = new HashMap<>();
    static HashMap<String,String> assignedOrderMap = new HashMap<>();
    public static void addOrder(Order order) {
        String orderID= order.getId();
        orderHashMap.put(orderID,order);
    }

    public static void addPartner(String partnerId) {
         DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
         deliveryPartnerHashMap.put(partnerId,deliveryPartner);
    }

    public static void addOrderPartnerPair(String orderId, String partnerId) {
        if(orderHashMap.containsKey(orderId) && deliveryPartnerHashMap.containsKey(partnerId))
        {
            List<Order> orderList=listHashMap.getOrDefault(partnerId,new ArrayList<>());
            Order order=orderHashMap.get(orderId);
            orderList.add(order);
            listHashMap.put(partnerId,orderList);
        }
        DeliveryPartner partnerOrder = deliveryPartnerHashMap.get(partnerId);
        int noOfOrders = partnerOrder.getNumberOfOrders();
        partnerOrder.setNumberOfOrders(noOfOrders+1);

        //Now order has been assigned
        assignedOrderMap.put(orderId, partnerId);
    }

    public static Order getOrderById(String orderId) {

        return orderHashMap.getOrDefault(orderId,null);
    }

    public static DeliveryPartner getPartnerById(String partnerId) {
        return deliveryPartnerHashMap.getOrDefault(partnerId,null);
    }

    public static List<String> getOrdersByPartnerId(String partnerId) {
        ArrayList<String> orderNames= new ArrayList<>();
        List<Order> orderList=listHashMap.get(partnerId);
        for(Order order : orderList){
            orderNames.add(order.getId());
        }
        return orderNames;
    }

    public static List<String> getAllOrders() {
       return new ArrayList<>(orderHashMap.keySet());
    }

    public static Integer getCountOfUnassignedOrders() {
        int ans=0;
        List<Order> orderList=new ArrayList<>(orderHashMap.values());
        HashMap<Order,Integer> map=new HashMap<>();
        for(List<Order> list : listHashMap.values())
        {
            for(Order order : list)
            {
                map.put(order,map.getOrDefault(order,0)+1);
            }
        }
        for(Order order:orderList)
        {
            if(!map.containsKey(order))
            {
                ans++;
            }
        }
        return ans;
    }

    public static Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int ordersLeft=0;
        String[] timeParts = time.split(":");
        int targetHours = Integer.parseInt(timeParts[0]);
        int targetMinutes = Integer.parseInt(timeParts[1]);
        int targetTimeInMinutes=targetHours * 60 + targetMinutes;

        List<Order> orderList = listHashMap.getOrDefault(partnerId, new ArrayList<>());
        for (Order order : orderList) {
            int deliveryTimeInMinutes = order.getDeliveryTime();
            if (deliveryTimeInMinutes > targetTimeInMinutes) {
                ordersLeft++;
            }
        }
        return ordersLeft;
    }

    public static String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<Order> orderList = listHashMap.get(partnerId);
        if (!orderList.isEmpty()) {
            Order lastOrder = orderList.get(orderList.size() - 1);
            int deliveryTimeInMinutes = lastOrder.getDeliveryTime();
            int hours = deliveryTimeInMinutes / 60;
            int minutes = deliveryTimeInMinutes % 60;
            return String.format("%02d:%02d", hours, minutes);
        }

        return null;
    }

    public static void deletePartnerById(String partnerId) {
        List<Order> orders = listHashMap.get(partnerId);

        for(Order order : orders)
        {
            assignedOrderMap.remove(order.getId());
        }

        listHashMap.remove(partnerId);
    }

    public static void deleteOrderById(String orderId) {
        orderHashMap.remove(orderId);


       if(assignedOrderMap.containsKey(orderId))
       {
           String partnerId = assignedOrderMap.get(orderId);
           DeliveryPartner deliveryPartner = deliveryPartnerHashMap.get(partnerId);
           int noOfOrders = deliveryPartner.getNumberOfOrders();
           deliveryPartner.setNumberOfOrders(noOfOrders-1);

           assignedOrderMap.remove(orderId);

           List<Order> orderList = listHashMap.get(deliveryPartner.getId());

           for(Order order : orderList)
           {
               String s = order.getId();
               if(s.equals(orderId))
               {
                   orderList.remove(order);
                   return;
               }
           }
       }

    }

    public static Integer getOrderCountByPartnerId(String partnerId) {
        List<Order> orderList=listHashMap.getOrDefault(partnerId,new ArrayList<>());
        return orderList.size();
    }
}
