package com.prasanth.oims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prasanth.oims.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long >{

    @Modifying
    @Query("""
        UPDATE Product p 
        SET p.quantity = p.quantity - :quantity 
        WHERE p.id= :productId 
            AND p.quantity >= :quantity
    """)
    int reserveInventory(@Param("productId") Long productId, @Param("quantity") Integer quantity);

}
