package com.Icwd.electronic.store.controllers;

import com.Icwd.electronic.store.dtos.*;
import com.Icwd.electronic.store.services.FileService;
import com.Icwd.electronic.store.services.ProductService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;

    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        log.info("In ProductController class createProduct method start");
        ProductDto createProduct = productService.create(productDto);
        log.info("In ProductController class createProduct method ended");
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        log.info("In ProductController class updateProduct method start with id:",productId);
        ProductDto updatedProduct = productService.update(productDto, productId);
        log.info("In ProductController class updateProduct method ended with id:",productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId, @RequestBody ProductDto productDto) {
        log.info("In ProductController class deleteProduct method start with id:",productId);
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product i deleted successfully !!").status(HttpStatus.OK).success(true).build();
        log.info("In ProductController class deleteProduct method ended with id:",productId);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        log.info("In ProductController class getProduct method start with id:",productId);
        ProductDto productDto = productService.get(productId);
        log.info("In ProductController class getProduct method ended with id:",productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    //getAll
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        log.info("In ProductController class getAll method start");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("In ProductController class getAll method ended");
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }


    //get all live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                   @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                   @RequestParam(value = "sortDir", defaultValue = "asc", required = false)String sortDir) {
        log.info("In ProductController class getAllLive method start");
        PageableResponse<ProductDto> productServiceAllLive = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("In ProductController class getAllLive method ended");
        return new ResponseEntity<>(productServiceAllLive,HttpStatus.OK);
    }

    //serchAll
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable String query,
                                                                      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                      @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
                                                                      @RequestParam(value = "sortDir", defaultValue = "asc", required = false)String sortDir){
        log.info("In ProductController class searchProduct method start with :",query);
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        log.info("In ProductController class searchProduct method ended with :",query);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    //update image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponce> uploadProductImage(
        @PathVariable String productId,
                @RequestParam("productImage")MultipartFile image) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);
        ImageResponce responce = ImageResponce.builder().imageName(updatedProduct.getProductImageName()).message("Product image is successfuly uploaded").status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(responce,HttpStatus.CREATED);
    }
    // Serve image
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        log.info("In UserController class serveUserImage method start with id:",productId);
        ProductDto productDto = productService.get(productId);
        InputStream resource = fileService.getResource(imagePath,productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("In UserController class serveUserImage method ended with id:",productId);

    }


}