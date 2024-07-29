package com.example.Ecomp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.Ecomp.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
    
    @Query("SELECT p FROM Product p WHERE "+
                    "LOWER(p.name) LIKE LOWER(CONCAT('%' , :keyword , '%')) OR "+
                    "LOWER(p.brand) LIKE LOWER(CONCAT('%' , :keyword , '%')) OR "+
                    "LOWER(p.catogory) LIKE LOWER(CONCAT('%' ,:keyword , '%'))")
    List<Product> searchProducts(String keyword);
}
