package com.rws.nitin.user.service.repository;

import com.rws.nitin.user.service.entity.Product;
import com.rws.nitin.user.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByUser(User user);
    List<Product> findByProductNameContaining(String productName);
}
