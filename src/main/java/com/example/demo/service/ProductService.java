package com.example.demo.service;

import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public ProductEntity createProducts(ProductEntity product,String username){
        Long userid = userRepository.getAuthUserId(username);
        ProductEntity productEntity = productRepository.save(
                new ProductEntity(
                        userid.intValue(),
                        product.getName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getPrice()
                )
        );
        return productEntity;
    }

    public ProductEntity updateProducts(Integer id, ProductEntity product){
        Optional<ProductEntity> productEntityData = productRepository.findById(id);
        ProductEntity productEntity = productEntityData.get();
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setPrice(product.getPrice());
        productRepository.save(productEntity);
        return productEntity;
    }

    public String deleteProduct(Integer id){
        productRepository.deleteById(id);
        return "deleted";
    }

    public List<ProductEntity> getAllProduct(String name, String description, Boolean outofstock){
        List<ProductEntity> productEntities = new ArrayList<>();

        if(outofstock==true){
            productRepository.findByNameAndDescription(name,description).stream().filter(q -> q.getQuantity() > 0).forEach(productEntities::add);
        }
        else{
            productRepository.findByNameAndDescription(name,description).forEach(productEntities::add);
        }
        return productEntities;
    }
}
