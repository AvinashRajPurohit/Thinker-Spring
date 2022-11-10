package com.deepak.cmsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.cmsapp.entities.Category;

public interface CategoryRepo extends JpaRepository <Category, Integer> {
    
}
