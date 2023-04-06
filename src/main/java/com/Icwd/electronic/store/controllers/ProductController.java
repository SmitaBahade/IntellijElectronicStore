package com.Icwd.electronic.store.controllers;

import com.Icwd.electronic.store.dtos.ApiResponseMessage;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.ProductDto;
import com.Icwd.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createProduct = productService.create(productDto);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.update(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product i deleted successfully !!").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //getAll
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }


    //get all live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                   @RequestParam(value = "sortDir", defaultValue = "asc", required = false)String sortDir) {
        PageableResponse<ProductDto> productServiceAllLive = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(productServiceAllLive,HttpStatus.OK);
    }

    //serchAll
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable String query,
                                                                      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                      @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                      @RequestParam(value = "sortDir", defaultValue = "asc", required = false)String sortDir){
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
}