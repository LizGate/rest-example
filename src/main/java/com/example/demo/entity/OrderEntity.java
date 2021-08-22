package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@ApiModel(value = "Order Api Model Documentation",description = "Model")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique id field of order object")
    private Integer id;
    @Column(name = "order_userid",nullable = false,length = 13)
    @ApiModelProperty(value = "User id field of order object")
    private Integer userid;
    @Column(name = "order_productid",nullable = false,length = 13)
    @ApiModelProperty(value = "Product id field of order object")
    private Integer productid;
    @Column(name = "order_quantity",nullable = false,length = 13)
    @ApiModelProperty(value = "Quantity field of order object")
    private Integer quantity;
    @Column(name = "order_totalprice",nullable = false)
    @ApiModelProperty(value = "Total price field of order object")
    private BigDecimal total;
    @Column(name = "order_status",nullable = false,length = 50)
    @ApiModelProperty(value = "Status field of order object")
    private String status;

    @ApiModelProperty(value = "Create time field of order object")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "Update time field of order object")
    private LocalDateTime updatedAt;


    public OrderEntity(){

    }

    public OrderEntity(Integer userid, Integer productid, Integer quantity, BigDecimal total) {
        this.userid = userid;
        this.productid = productid;
        this.quantity = quantity;
        this.total = total;
        this.status = "CREATED";
        this.createdAt = LocalDateTime.now();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
