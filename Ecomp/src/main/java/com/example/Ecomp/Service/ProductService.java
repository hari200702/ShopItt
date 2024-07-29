package com.example.Ecomp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Ecomp.Repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import com.example.Ecomp.Model.Product;;

@Service
public class ProductService {
    

    @Autowired
    private ProductRepository repo;


    public List<Product> getallProducts(){
        return repo.findAll();
    }

    public Product geProductbyId(int id){
        return repo.findById(id).get();
    }
    public Product addProduct(Product product,MultipartFile imageFile) throws IOException{
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id,Product product,MultipartFile imagFile) throws IOException{
        product.setImageData(imagFile.getBytes());
        product.setImageName(imagFile.getOriginalFilename());
        product.setImageType(imagFile.getContentType());
        return repo.save(product);

    }

    public void deleteProduct(int id){
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword){
        return repo.searchProducts(keyword);
    }
}
