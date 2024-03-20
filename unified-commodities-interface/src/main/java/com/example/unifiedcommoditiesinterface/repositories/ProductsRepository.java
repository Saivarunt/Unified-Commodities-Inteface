package com.example.unifiedcommoditiesinterface.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.unifiedcommoditiesinterface.models.Products;
import com.example.unifiedcommoditiesinterface.models.User;
import java.util.List;


@Repository
public interface ProductsRepository extends MongoRepository<Products, String>{
    List<Products> findByOwner(User owner);
    Page<Products> findByOwner(User owner, Pageable pageable);
    @Query("{product_name:/?0/}")
    Page<Products> findByProduct_name(String product_name, Pageable pageable);
}
