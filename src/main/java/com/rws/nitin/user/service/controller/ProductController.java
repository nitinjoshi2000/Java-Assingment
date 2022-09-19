package com.rws.nitin.user.service.controller;
import com.rws.nitin.user.service.payloads.ApiResponse;
import com.rws.nitin.user.service.payloads.ProductDto;
import com.rws.nitin.user.service.service.FileService;
import com.rws.nitin.user.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

//    create
    @PostMapping("/user/{userId}/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable Integer userId){
        ProductDto createProduct = this.productService.createProduct(productDto,userId);
        return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);

    }

//  update
    @PutMapping("products/{proId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Integer proId){
        ProductDto updatedProduct = this.productService.updateProduct(productDto, proId);
        return new ResponseEntity<ProductDto>(updatedProduct, HttpStatus.OK);

    }

//   Delete
    @DeleteMapping("products/{proId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer proId){
        this.productService.deleteProduct(proId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Product is deleted successfully",true),
                HttpStatus.OK);

    }

//   Get All Products
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> allProducts = this.productService.getAllProducts();
        return new ResponseEntity<List<ProductDto>>(allProducts,HttpStatus.OK);
    }

//   Get Product By id
    @GetMapping("products/{proId}")
      public ResponseEntity<ProductDto> getProductById(@PathVariable Integer proId){
      ProductDto productDto = this.productService.getProductById(proId);
      return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
    }

//   Get Product By User
    @GetMapping("/user/{userId}/products")
    public ResponseEntity <List<ProductDto>> getProductByUser( @PathVariable Integer userId){
        List<ProductDto> products = this.productService.getProductsByUser(userId);
        return new ResponseEntity<List<ProductDto>>(products,HttpStatus.OK);

    }
//    Search via keyword
    @GetMapping("/products/search/{keywords}")
    public ResponseEntity<List<ProductDto>> searchProductByName(@PathVariable("keywords")String keywords){
        List<ProductDto>result = this.productService.searchProducts(keywords);
        return new ResponseEntity<List<ProductDto>>(result,HttpStatus.OK);
    }

//    Product image upload
    @PostMapping("/products/image/upload/{productId}")
    public ResponseEntity<ProductDto>uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable Integer productId) throws IOException {
        ProductDto productDto = this.productService.getProductById(productId);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setProductImage(fileName);
        ProductDto updateProduct = this.productService.updateProduct(productDto,productId);
        return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.OK);
    }

//    Method to serve files
    @GetMapping(value = "/products/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
