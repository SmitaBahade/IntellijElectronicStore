package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.dtos.ProductDto;
import com.Icwd.electronic.store.entities.Product;
import com.Icwd.electronic.store.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper mapper;

    Product product;
    ProductDto productDto;
    Product product1;
    Product product2;
    @BeforeEach
    public void init(){
        product=Product.builder()
                .title("TV")
                .description("Tv have very good quality")
                .price(50000)
                .discountedPrice(45000)
                .quantity(1)
                .build();

        productDto=ProductDto.builder().title("Redio")
                .description("Redio have very good quality")
                .price(23000)
                .discountedPrice(18000)
                .quantity(1)
                .build();

        product1=Product.builder()
                .title("Mobial")
                .description("Mobial have very good quality")
                .price(50000)
                .discountedPrice(45000)
                .quantity(1)
                .build();

        product2=Product.builder()
                .title("Laptop")
                .description("Laptop have very good quality")
                .price(50000)
                .discountedPrice(45000)
                .quantity(1)
                .build();
    }
    @Test
    public void createTest(){
        //arrenge
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        //act
        ProductDto product1 = productService.create(mapper.map(product, ProductDto.class));
        //assert
        Assertions.assertNotNull(product1);
        Assertions.assertEquals("TV",product1.getTitle());
    }
    @Test
    public void updateTest(){
        String productId="frtyu";
        //arrenge
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        //act
        ProductDto updatedProduct = productService.update(productDto, productId);
        //assert
        System.out.println(updatedProduct.getTitle());
        Assertions.assertEquals("Redio",updatedProduct.getTitle());
        Assertions.assertNotNull("Redio",updatedProduct.getTitle());
    }
    @Test
    public void deleteTest(){
        String productid="zxcv";
        //arrenge
        Mockito.when(productRepository.findById("zxcv")).thenReturn(Optional.of(product));
        //acta
        productService.delete(productid);
        //assert
        Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }
    @Test
    public void getAllTest(){
        //arrenge
        List<Product> productList = Arrays.asList(product, product1, product2);
        Page page= new PageImpl(productList);
        Mockito.when(productRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
        //act
        PageableResponse<ProductDto> allProduct = productService.getAll(1, 2, "title", "asc");
        //assert
        Assertions.assertEquals(3,allProduct.getContent().size());
    }
//    @Test
//    public void searchByTitleTest(){
//        String keywords="TV";
//        //arrenge
//        Mockito.when(productRepository.findByTitleContaining((Pageable)Mockito.any())).thenReturn(Arrays.asList(product,product1,product2));
//        //acta
//        productService.searchByTitle(keywords);
//        //assert
    }

