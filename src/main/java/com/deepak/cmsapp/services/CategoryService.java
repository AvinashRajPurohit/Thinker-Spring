package com.deepak.cmsapp.services;

import java.util.List;

import com.deepak.cmsapp.payloads.CategoryDto;

public interface CategoryService {
    
    CategoryDto createCategory(CategoryDto categoryDto); // Create
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId); // Update
    void deleteCategory(Integer categoryId); // Delete
    CategoryDto getCategory(Integer categoryId); // get
    List<CategoryDto> getAllCategory(); //get all

}
