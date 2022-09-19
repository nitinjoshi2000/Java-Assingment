package com.rws.nitin.user.service.service.impl;

import com.rws.nitin.user.service.entity.Product;
import com.rws.nitin.user.service.entity.User;
import com.rws.nitin.user.service.exceptions.ResourceNotFoundException;
import com.rws.nitin.user.service.payloads.ProductDto;
import com.rws.nitin.user.service.repository.ProductRepository;
import com.rws.nitin.user.service.repository.UserRepository;
import com.rws.nitin.user.service.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto createProduct(ProductDto productDto, Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User",userId));
        Product pro = this.modelMapper.map(productDto, Product.class);
        pro.setProductImage("default.png");
        pro.setUser(user);
        Product addedPro = this.productRepository.save(pro);
        return this.modelMapper.map(addedPro, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product pro = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","product id",productId));
        pro.setProductName(productDto.getProductName());
        pro.setProductPrice(productDto.getProductPrice());
        pro.setProductColor(productDto.getProductColor());
        pro.setProductImage(productDto.getProductImage());
        Product updatedpro = this.productRepository.save(pro);
        return this.modelMapper.map(updatedpro, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product pro = this.productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","product id", productId));
        this.productRepository.delete(pro);

    }

    @Override
    public ProductDto getProductById(Integer productId) {
        Product pro = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "product id", productId));
        return this.modelMapper.map(pro, ProductDto.class);
    }


    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> allproducts = this.productRepository.findAll();
        List<ProductDto> proDtos = allproducts.stream().map((pro) -> this.modelMapper.map(pro, ProductDto.class)).collect(Collectors.toList());
        return proDtos;
    }

    @Override
    public List<ProductDto> getProductsByUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
        List<Product> products = this.productRepository.findByUser(user);
        List<ProductDto> productDtos = products.stream().map((pro) -> this.modelMapper.map(pro, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }
    @Override
    public List<ProductDto> searchProducts(String Keyword) {
        List<Product> products = this.productRepository.findByProductNameContaining(Keyword);
        List<ProductDto> productDtos = products.stream().map((product)-> this.modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
        return productDtos ;
    }


}
