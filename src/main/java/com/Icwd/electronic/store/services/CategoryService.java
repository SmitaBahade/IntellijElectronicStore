package com.Icwd.electronic.store.services;

import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);
    //update
    CategoryDto update(CategoryDto categoryDto, String CategoryId);

    //delete
    void delete(String categoryId);
    //getAll
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //getSingleCategoryDetail
    CategoryDto get(String categoryId);

    //search
}
