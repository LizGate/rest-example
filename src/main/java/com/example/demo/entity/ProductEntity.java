package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@ApiModel(value = "Product Api Model Documentation",description = "Model")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Unique id field of product object")
    private Integer id;
    @Column(name = "product_userid",nullable = false,length = 13)
    @ApiModelProperty(value = "User id field of product object")
    private Integer userid;
    @Column(name = "product_name",nullable = false,length = 50)
    @ApiModelProperty(value = "Name field of product object")
    private String name;
    @Column(name = "product_description",nullable = false,columnDefinition = "TEXT")
    @ApiModelProperty(value = "Description field of product object")
    private String description;
    @Column(name = "product_quantity", nullable = false,length = 13)
    @ApiModelProperty(value = "Quantity field of product object")
    private Integer quantity;
    @Column(name = "product_price",nullable = false,length = 13)
    @ApiModelProperty(value = "Price field of product object")
    private BigDecimal price;


    public ProductEntity(){

    }

    public ProductEntity(Integer userid, String name, String description, Integer quantity, BigDecimal price) {
        this.userid = userid;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
