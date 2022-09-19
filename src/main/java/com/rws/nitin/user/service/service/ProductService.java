package com.rws.nitin.user.service.service;

import com.rws.nitin.user.service.entity.Product;
import com.rws.nitin.user.service.payloads.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {

//    Create
    ProductDto createProduct(ProductDto productDto, Integer userId);
//    Update
    ProductDto updateProduct(ProductDto productDto, Integer productId);
    //    Delete
    void deleteProduct(Integer productId);
//    Get
    ProductDto getProductById(Integer productId);
//    GetAll
    List<ProductDto> getAllProducts();
//    Get all by user
    List<ProductDto> getProductsByUser(Integer userId);

//    Search Products(via Keyword)
    List<ProductDto> searchProducts(String Keyword);
}
