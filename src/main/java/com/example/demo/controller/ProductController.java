package com.example.demo.controller;

import com.example.demo.entity.ProductEntity;
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
@RequestMapping("api/v1/product")
@Api(value = "Product Api Documentation")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PostMapping("create")
    @ApiOperation(value = "Product create method")
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product){
        try{
            String username = userService.currentUserName();
            ProductEntity productEntity = productService.createProducts(product,username);
            return new ResponseEntity<>(productEntity,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    @ApiOperation(value = "Product update method")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Integer id,@RequestBody ProductEntity product){
        try {
            ProductEntity productEntity = productService.updateProducts(id,product);
            return new ResponseEntity<>(productEntity,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "Product delete method")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Integer id){
        try{
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("all")
    @ApiOperation(value = "Product list method")
    public ResponseEntity<List<ProductEntity>> getAllProducts(@RequestParam(required = false,defaultValue = "") String name, @RequestParam(required = false,defaultValue = "") String description, @RequestParam(required = false,defaultValue = "false") Boolean outofstock){
        try{
            List<ProductEntity> productEntity = productService.getAllProduct(name,description,outofstock);
            return new ResponseEntity<>(productEntity,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
