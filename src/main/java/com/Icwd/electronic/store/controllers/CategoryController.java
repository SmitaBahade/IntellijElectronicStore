package com.Icwd.electronic.store.controllers;

import com.Icwd.electronic.store.dtos.ApiResponseMessage;
import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    //create
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        //call service to save object
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto){
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    //delet
        @DeleteMapping("/{categoryId}")
        public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.delete(categoryId);
            ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category deleted seccessfully!!").status(HttpStatus.OK).success(true).build();
            return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
    //getAll
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>>getAll(@RequestParam( value = "pageNumber", defaultValue="0",required =false)int pageNumber,
                                                               @RequestParam(value = "pageSize", defaultValue="0",required =false)int pageSize,
                                                               @RequestParam( value = "sortBy", defaultValue="title",required =false)String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue="asc",required =false)String sortDir){
       PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //getSingle
        @GetMapping("/{categoryId}")
        public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId ){
            CategoryDto categoryDto = categoryService.get(categoryId);
            return ResponseEntity.ok(categoryDto);
        }
    }

