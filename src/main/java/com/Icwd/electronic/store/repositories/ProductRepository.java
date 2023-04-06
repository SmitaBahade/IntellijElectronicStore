package com.Icwd.electronic.store.repositories;

import com.Icwd.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    //search
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);

   Page<Product> findByLiveTrue(Pageable pageable);

    //other methods
    // custome finder method
    // query method
}
