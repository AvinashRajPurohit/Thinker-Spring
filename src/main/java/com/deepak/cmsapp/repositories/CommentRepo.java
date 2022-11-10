package com.deepak.cmsapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepak.cmsapp.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
    
}
