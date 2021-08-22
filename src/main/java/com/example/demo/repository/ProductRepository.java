package com.example.demo.repository;

import com.example.demo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query("SELECT p FROM ProductEntity p WHERE p.name LIKE CONCAT('%',:name,'%')")
    List<ProductEntity> findByName(@Param("name") String name);
    List<ProductEntity> findByDescription(String description);

    @Query("SELECT p FROM ProductEntity p WHERE p.name LIKE CONCAT('%',:name,'%') AND p.description LIKE CONCAT('%',:description,'%')")
    List<ProductEntity> findByNameAndDescription(@Param("name")String name, @Param("description")String description);


}
