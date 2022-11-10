package com.deepak.cmsapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.cmsapp.payloads.CategoryDto;
import com.deepak.cmsapp.services.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService catService;

    // create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto){
        CategoryDto createdCat = this.catService.createCategory(catDto);
        return new ResponseEntity<CategoryDto>(createdCat, HttpStatus.CREATED);
    }


    // update
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto, @PathVariable Integer catId){
        CategoryDto updatedCat = this.catService.updateCategory(catDto, catId);
        return new ResponseEntity<CategoryDto>(updatedCat, HttpStatus.OK);
    }

    
    // Delete
    @DeleteMapping("/{catId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer catId){
        this.catService.deleteCategory(catId);
        return new ResponseEntity<String>("Category Deleted Successfully..", HttpStatus.OK);
    }

    // get
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
        CategoryDto category = this.catService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }

    // getall
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categories = this.catService.getAllCategory();
        return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
    }


}
