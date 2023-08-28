package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    public static void addOrder(Order order) {
        OrderRepository.addOrder(order);
    }

    public static void addPartner(String partnerId) {
        OrderRepository.addPartner(partnerId);
    }

    public static void addOrderPartnerPair(String orderId, String partnerId) {
        OrderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public static Order getOrderById(String orderId) {

        return OrderRepository.getOrderById(orderId);
    }

    public static DeliveryPartner getPartnerById(String partnerId) {

        return OrderRepository.getPartnerById(partnerId);
    }

    public static List<String> getOrdersByPartnerId(String partnerId) {
        return OrderRepository.getOrdersByPartnerId(partnerId);
    }

    public static List<String> getAllOrders() {

        return OrderRepository.getAllOrders();
    }

    public static Integer getCountOfUnassignedOrders() {

        return OrderRepository.getCountOfUnassignedOrders();
    }

    public static Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return OrderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
    }

    public static String getLastDeliveryTimeByPartnerId(String partnerId) {
        return OrderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public static void deletePartnerById(String partnerId) {

        OrderRepository.deletePartnerById(partnerId);
    }

    public static void deleteOrderById(String orderId) {
        OrderRepository.deleteOrderById(orderId);
    }

    public static Integer getOrderCountByPartnerId(String partnerId) {
       return OrderRepository.getOrderCountByPartnerId(partnerId);
    }
}
