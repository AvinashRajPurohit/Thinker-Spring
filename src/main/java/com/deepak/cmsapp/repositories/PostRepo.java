package com.deepak.cmsapp.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.cmsapp.entities.Category;
import com.deepak.cmsapp.entities.Post;
import com.deepak.cmsapp.entities.CustomUser;

public interface PostRepo extends JpaRepository<Post, Integer> {
    
    Page<Post> findByCustomUser(CustomUser customUser, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

}
