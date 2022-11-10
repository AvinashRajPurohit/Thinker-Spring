package com.deepak.cmsapp.services;


import com.deepak.cmsapp.payloads.PostDto;
import com.deepak.cmsapp.payloads.PostResponse;
import com.deepak.cmsapp.enums.SortingOrder;

public interface PostService {
    
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId); // create 
    PostDto updatePost(PostDto postDto, Integer postId); // update
    void deletePost(Integer postId);  // delete
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, SortingOrder sortOrder); // list
    PostDto getPostById(Integer postId); // get
    PostResponse getPostsByCategory(Integer pageNumber, Integer pageSize, Integer categoryId); // list by cat
    PostResponse getPostsByUser(Integer pageNumber, Integer pageSize, Integer userId); // list by user
    PostResponse searchPosts(Integer pageNumber, Integer pageSize, String keyword);



}
