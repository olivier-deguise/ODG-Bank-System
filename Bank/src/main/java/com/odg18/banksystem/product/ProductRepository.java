package com.odg18.banksystem.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    @Override
    List<Product> findAllById(Iterable<Long> longs);

}
