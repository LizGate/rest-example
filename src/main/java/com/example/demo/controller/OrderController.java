package com.example.demo.controller;

import com.example.demo.entity.OrderEntity;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@Api(value = "Order Api Documentation")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PostMapping("create")
    @ApiOperation(value = "Order create method")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order){
        try{
            String username = userService.currentUserName();
            OrderEntity orderEntity = orderService.createOrder(order,username);
            return new ResponseEntity<>(orderEntity, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    @ApiOperation(value = "Order accept and delivered method")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Integer id){
        try{
            OrderEntity orderEntity = orderService.updateOrder(id);
            return new ResponseEntity<>(orderEntity,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list/seller")
    @ApiOperation(value = "Order list for seller method")
    public ResponseEntity<List<OrderEntity>> getSellerOrder(){
        try {
            String username = userService.currentUserName();
            List<OrderEntity> orderEntity = orderService.listSellerOrder(username);
            return new ResponseEntity<>(orderEntity,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("list/user")
    @ApiOperation(value = "Order list for customer method")
    public ResponseEntity<List<OrderEntity>> getUserOrder(){
        try{
            String username = userService.currentUserName();
            List<OrderEntity> orderEntity = orderService.listUserOrder(username);
            return new ResponseEntity<>(orderEntity,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("reject/{id}")
    @ApiOperation(value = "Order reject method")
    public ResponseEntity<OrderEntity> rejectOrder(@PathVariable Integer id){
        try{
            OrderEntity orderEntity = orderService.rejectOrder(id);
            return new ResponseEntity<>(orderEntity,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("cancel/{id}")
    @ApiOperation(value = "Order cancel method")
    public ResponseEntity<OrderEntity> cancelOrder(@PathVariable Integer id){
        try{
            OrderEntity orderEntity = orderService.cancelOrder(id);
            return new ResponseEntity<>(orderEntity,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
