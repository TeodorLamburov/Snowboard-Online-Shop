package onlineshop.service;

import onlineshop.model.dtos.OrderServiceModel;

public interface OrderService {

    void createOrder(OrderServiceModel orderServiceModel);
}
