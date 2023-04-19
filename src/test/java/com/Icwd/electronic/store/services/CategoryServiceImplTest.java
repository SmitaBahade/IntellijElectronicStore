package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.entities.Category;
import com.Icwd.electronic.store.repositories.CategoryRepository;
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
public class CategoryServiceImplTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    Category category;
    CategoryDto categoryDto;
    Category category1;
    Category category2;
    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("Offer")
                .description("Offer valid till today")
                .coverImage("abc.png").
                build();

        categoryDto = CategoryDto.builder()
                .title("Coupon")
                .description("Coupon valid till today")
                .coverImage("xyz.png").
                build();
        category1 = Category.builder()
                .title("Sale")
                .description("Sale valid till today")
                .coverImage("abc.png").
                build();
        category2 = Category.builder()
                .title("bumparOffer")
                .description("bumparOffer valid till today")
                .coverImage("abc.png").
                build();
    }
    @Test
    public void createTest(){
        //arrenge
       Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        //act
        CategoryDto category1 = categoryService.create(mapper.map(category, CategoryDto.class));
      //  System.out.println(category1.getTitle());
        //assert
        Assertions.assertNotNull(category1);
        Assertions.assertEquals("Offer",category1.getTitle());
    }
    @Test
    public void update(){
        String categoryId="uyrghgfi";
        //arrenge
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        //act
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
       System.out.println(updatedCategory.getTitle());
        //asserst
        Assertions.assertEquals("Coupon",updatedCategory.getTitle());
        Assertions.assertNotNull(categoryDto);
    }
    @Test
    public void deleteTest(){
        String categoryid="uvxyz";
        //arrenge
        Mockito.when(categoryRepository.findById("uvxyz")).thenReturn(Optional.of(category));
        //acta
        categoryService.delete(categoryid);
        //assert
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }
    @Test
    public void getAll(){
        //arrenge
        List<Category> categoryList = Arrays.asList(category, category1, category2);
        Page page = new PageImpl(categoryList);
        Mockito.when(categoryRepository.findAll((Pageable)Mockito.any())).thenReturn(page);
        //acta
        PageableResponse<CategoryDto> allCategory = categoryService.getAll(1, 2, "title", "asc");
        //assert
        Assertions.assertEquals(3,allCategory.getContent().size());
    }

    //arrenge
    //acta
    //assert
}
