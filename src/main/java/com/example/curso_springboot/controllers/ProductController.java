package com.example.curso_springboot.controllers;

import com.example.curso_springboot.dto.ProductRecordDTO;
import com.example.curso_springboot.model.ProductModel;
import com.example.curso_springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/products")// Boas práticas Restful
    public ResponseEntity<List<ProductModel>> getAllProducts(){//Gera uma lista de ProductModel
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());//Retorna todos os produtos
    }

    @GetMapping("/products/{id}")// Boas práticas Restful
    public ResponseEntity<Object> getProductById(@PathVariable(value = "id") UUID id){//Busca um produto pelo ID, o value = "id", indica que o valor da URL deve ser mapeado pra o parâmetro do método
        Optional<ProductModel> productO = productRepository.findById(id);//Optional retorna um objeto ou pode estar vazia
        if (productO.isEmpty()){//Checagem pelo Optional, se estiver vazia informa que n foi encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());//Retorna o produto
    }

    @PutMapping("/products/{id}")// Boas práticas Restful
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid ProductRecordDTO productRecordDTO){ //Busca um produto pelo ID e recebe o corpo da requisição para atualizar e validar
        Optional<ProductModel> productO = productRepository.findById(id);//Optional retorna um objeto ou pode estar vazia
        if (productO.isEmpty()){//Checagem pelo Optional, se estiver vazia informa que n foi encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = new ProductModel(); //Instanciando um objeto do tipo productModel
        BeanUtils.copyProperties(productRecordDTO, productModel); //Converte um DTO em productModel
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));//Salva o objeto atualizado pelo ID específico.
    }

    @DeleteMapping("/products/{id}")// Boas práticas Restful
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){ //Busca um produto pelo ID.
        Optional<ProductModel> productO = productRepository.findById(id);//Optional retorna um objeto ou pode estar vazia
        if (productO.isEmpty()){//Checagem pelo Optional, se estiver vazia informa que n foi encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productO.get());//Chama o método delete do JPA
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted succeessfully");//Confirma a exclusão do produto
    }

}
