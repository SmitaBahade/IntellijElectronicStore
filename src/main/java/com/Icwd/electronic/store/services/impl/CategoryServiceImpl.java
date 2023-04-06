package com.Icwd.electronic.store.services.impl;

import com.Icwd.electronic.store.dtos.CategoryDto;
import com.Icwd.electronic.store.dtos.PageableResponse;
import com.Icwd.electronic.store.entities.Category;
import com.Icwd.electronic.store.exceptions.ResourceNotFoundException;
import com.Icwd.electronic.store.helper.Helper;
import com.Icwd.electronic.store.repositories.CategoryRepository;
import com.Icwd.electronic.store.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        log.info("Initiating the dao call for create the Category data ");
        Category category   = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        log.info("Complete the dao call for create the Category data :");
        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        log.info("Initiating the dao call for update the Category data with id{}:",categoryId);
        //get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception !!"));
        //update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        log.info("Complete the dao call for update the Category data with id{}:",categoryId);
        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        log.info("Initiating the dao call for delete the Category data with id{}:",categoryId);
        //get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception !!"));
        categoryRepository.delete(category);
        log.info("Complete the dao call for delete the Category data with id{}:",categoryId);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        log.info("Initiating the dao call for getAll the Category data ");
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
       Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        log.info("Complete the dao call for getAll the Category data :");
        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        log.info("Initiating the dao call for get the Category data with id{}:",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found Exception !!"));
        log.info("Complete the dao call for get the Category data with id{}:",categoryId);
        return mapper.map(category,CategoryDto.class);
    }
}
