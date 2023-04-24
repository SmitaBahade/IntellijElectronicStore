package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.ProductDto;
import com.Icwd.electronic.store.entities.Product;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.helper.Helper;
import com.Icwd.electronic.store.repositories.ProductRepository;
import com.Icwd.electronic.store.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto create(ProductDto productDto) {
        log.info("Initiating the dao call for create the product data ");
        Product product = mapper.map(productDto, Product.class);
        //product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product);
        log.info("Complete the dao call for create the product data :");
        return mapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        log.info("Initiating the dao call for update the product data with id{}:",productId);
        //fetch the product of given id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        //save the entity
        Product updatedProduct = productRepository.save(product);
        log.info("Complete the dao call for update the product data with id{}:",productId);
        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        log.info("Initiating the dao call for delete the product data with id{}:",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        productRepository.delete(product);
        log.info("Complete the dao call for delete the product data with id{}:",productId);
    }

    @Override
    public ProductDto get(String productId) {
        log.info("Initiating the dao call to get product from data with id{}:",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        log.info("Complete the dao call to get product from data with id{}:",productId);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call to get all product from data ");
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        log.info("Complete the dao call to get all product from data");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call to get all live product from data ");
        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        log.info("Complete the dao call to get all live product from data");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
             log.info("Initiating the dao call to get product by searchByTitle from data ");
            Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
            Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
            Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
             log.info("Complete the dao call to get product by searchByTitle from data");
            return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        return null;
    }
}
