package com.example.Ecomp.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.example.Ecomp.Model.Product;
import com.example.Ecomp.Service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Controller {

    @Autowired
    private ProductService service;

    
    @GetMapping("/products")
    public ResponseEntity<List <Product>> getallProducts(){
        return new ResponseEntity<>(service.getallProducts(),HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        return new ResponseEntity<>(service.geProductbyId(id),HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
    @RequestPart MultipartFile imageFile){

        try{
            System.out.println(product);
            Product product1= service.addProduct(product,imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImagebyProductId(@PathVariable int id){

        Product product =service.geProductbyId(id);
        byte[] imageFile=product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile imageFile){

        Product product1=null;
        try{
            product1=service.updateProduct(id,product,imageFile);
        }catch(IOException e){
            return new ResponseEntity<>("Failedtoupdate",HttpStatus.BAD_REQUEST);
         }
        
         if(product1!=null){
            return new ResponseEntity<>("Updated",HttpStatus.OK);
         }
         else{
            return new ResponseEntity<>("Lailedtoupdate",HttpStatus.BAD_REQUEST);

    }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product=service.geProductbyId(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List <Product> products=service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
