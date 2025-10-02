package com.Lifeisgood.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Lifeisgood.products.ProductEntity; // Import your entity

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long>{

}
