package com.example.curso_springboot.controllers;

import com.example.curso_springboot.dto.ProductRecordDTO;
import com.example.curso_springboot.model.ProductModel;
import com.example.curso_springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products") // Boas práticas Restful
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDTO productRecordDTO){
        var productModel = new ProductModel(); //Instanciando um objeto do tipo productModel
        BeanUtils.copyProperties(productRecordDTO, productModel); //Converte um DTO em productModel
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));//Retorna a resposta e adiciona o corpo do método
    }
}
