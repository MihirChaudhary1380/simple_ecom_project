package com.mihir.SpringEcom.controller;

import com.mihir.SpringEcom.model.Product;
import com.mihir.SpringEcom.service.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductServices productservices;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts()
    {

        return new ResponseEntity<>( productservices.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product=productservices.getProductById(id);

        if(product.getId() >- 1) {
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(product,HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageProductId(@PathVariable  int productId){

        Product product=productservices.getProductById(productId);

        if(product.getId() >- 1) {
            return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(product.getImageData(),HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product saveProduct= null;
        try {
            saveProduct = productservices.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>(saveProduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }



    }
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id ,@RequestPart Product product,@RequestPart MultipartFile imageFile){
        Product updatedProduct =null;
        try{
            updatedProduct=productservices.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>("updated",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id ){
        Product product=productservices.getProductById(id);
        if(product != null){
            productservices.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/products/search")
        public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
            List<Product> products= productservices.searchProducts(keyword);
        System.out.println("Searching with "+keyword);
            return new ResponseEntity<>(products,HttpStatus.OK);

        }

    }

