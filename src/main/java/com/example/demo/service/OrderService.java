package com.example.demo.service;

import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public OrderEntity createOrder(OrderEntity order,String username){
        Long userid = userRepository.getAuthUserId(username);
        Optional<ProductEntity> productEntity = productRepository.findById(order.getProductid());
        Integer orderQuantity = productEntity.orElseThrow(RuntimeException::new).getQuantity();
        BigDecimal productPrice = productEntity.orElseThrow(RuntimeException::new).getPrice();

        if(orderQuantity>=order.getQuantity()){
            OrderEntity orderEntity = orderRepository.save(
                    new OrderEntity(
                            userid.intValue(),
                            order.getProductid(),
                            order.getQuantity(),
                            productPrice.multiply(BigDecimal.valueOf(order.getQuantity()))
                    )
            );
            return orderEntity;
        }
        else{
            return null;
        }

    }

    public OrderEntity updateOrder(Integer id){
        Optional<OrderEntity> orderEntityData = orderRepository.findById(id);
        OrderEntity orderEntity = orderEntityData.get();
        String orderStatus = orderEntityData.orElseThrow(RuntimeException::new).getStatus();

        if(orderStatus.equals("CREATED")){
            orderEntity.setStatus("ACCEPTED");
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(orderEntity);
        }
        else if(orderStatus.equals("ACCEPTED")){
            orderEntity.setStatus("DELIVERED");
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(orderEntity);
        }
        else{
            orderEntity.setStatus(orderStatus);
            orderRepository.save(orderEntity);
        }
        return orderEntity;
    }

    public OrderEntity rejectOrder(Integer id){
        Optional<OrderEntity> orderEntityData = orderRepository.findById(id);
        OrderEntity orderEntity = orderEntityData.get();
        String orderStatus = orderEntityData.orElseThrow(RuntimeException::new).getStatus();

        if(orderStatus.equals("CREATED")){
            orderEntity.setStatus("REJECTED");
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(orderEntity);
        }

        return orderEntity;
    }

    public OrderEntity cancelOrder(Integer id){
        Optional<OrderEntity> orderEntityData = orderRepository.findById(id);
        OrderEntity orderEntity = orderEntityData.get();
        String orderStatus = orderEntityData.orElseThrow(RuntimeException::new).getStatus();

        if(orderStatus.equals("CREATED")){
            orderEntity.setStatus("CANCELLED");
            orderEntity.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(orderEntity);
        }

        return orderEntity;
    }

    public List<OrderEntity> listSellerOrder(String username){
        Long userid = userRepository.getAuthUserId(username);
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderRepository.getSellerOrder(userid.intValue()).forEach(orderEntities::add);
        return orderEntities;
    }

    public List<OrderEntity> listUserOrder(String username){
        Long userid = userRepository.getAuthUserId(username);
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderRepository.getUserOrder(userid.intValue()).forEach(orderEntities::add);
        return orderEntities;
    }




}
