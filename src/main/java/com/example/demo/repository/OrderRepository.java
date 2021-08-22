package com.example.demo.repository;

import com.example.demo.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query("SELECT o FROM OrderEntity o WHERE o.productid IN (SELECT p.id FROM ProductEntity p WHERE p.userid=:seller)")
    List<OrderEntity> getSellerOrder(@Param("seller")Integer id);

    @Query("SELECT o FROM OrderEntity o WHERE o.userid=:user")
    List<OrderEntity> getUserOrder(@Param("user")Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET order_status='DELIVERED' WHERE order_status='ACCEPTED' and TIMESTAMPDIFF(MINUTE,updated_at,NOW()) > 2 ORDER BY RAND() LIMIT 1",nativeQuery = true)
    void updateRandomStatusOrder();
}

/*

@Modifying
@Transactional
@Query("SELECT p.product_userid AS seller, DATE(o.updated_at) AS date, SUM(o.order_totalprice) AS totalProfit
FROM orders AS o
INNER JOIN products AS p ON o.order_productid = p.id AND DATE(o.updated_at) = CURDATE()-1 AND o.order_status='DELIVERED'
GROUP BY p.product_userid;",nativeQuery=true)
void dailyProfitReports();

 */