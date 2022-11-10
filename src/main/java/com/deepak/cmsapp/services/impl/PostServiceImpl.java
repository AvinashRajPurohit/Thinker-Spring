package com.deepak.cmsapp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deepak.cmsapp.entities.Category;
import com.deepak.cmsapp.entities.Post;
import com.deepak.cmsapp.entities.CustomUser;
import com.deepak.cmsapp.enums.SortingOrder;
import com.deepak.cmsapp.exceptions.ResourceNotFoundException;
import com.deepak.cmsapp.payloads.PostDto;
import com.deepak.cmsapp.payloads.PostResponse;
import com.deepak.cmsapp.repositories.CategoryRepo;
import com.deepak.cmsapp.repositories.PostRepo;
import com.deepak.cmsapp.repositories.UserRepo;
import com.deepak.cmsapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        CustomUser user = this.userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "User", " User Id ", userId));

        Category category = this.categoryRepo.findById(categoryId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                "Category", " Category Id ", categoryId));
                                
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCustomUser(user);
        post.setCategory(category);

        Post createdPost = this.postRepo.save(post);
        return this.modelMapper.map(createdPost, PostDto.class);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(
            "Post", " Post Id ", postId));
        
            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setImageName(postDto.getImageName());

            Post updatedPost = this.postRepo.save(post);
            return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(
            "Post", " Post Id ", postId));

        this.postRepo.delete(post);
        
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, SortingOrder sortOrder) {
        Sort s;
        if (sortOrder == SortingOrder.ASC){
            s = Sort.by(sortBy).ascending();
        }else{
            s = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize, s);

        Page<Post> pagePost = this.postRepo.findAll(p);

        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map((post ->
                           this.modelMapper.map(post, PostDto.class)))
                           .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastpage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(
            "Post", " Post Id ", postId));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getPostsByCategory(Integer pageNumber, Integer pageSize, Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Category", " Category Id ", categoryId));
        Pageable p = PageRequest.of(pageNumber, pageSize);


        Page<Post> pagePost = this.postRepo.findByCategory(category, p);
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map((post -> 
                        this.modelMapper.map(post, 
                        PostDto.class))).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastpage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getPostsByUser(Integer pageNumber, Integer pageSize, Integer userId) {
        
        CustomUser customUser = this.userRepo.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException(
            "User", " User Id ", userId));
        
        Pageable p = PageRequest.of(pageNumber, pageSize);


        Page<Post> pagePost = this.postRepo.findByCustomUser(customUser, p);
        
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map((post -> 
                        this.modelMapper.map(post, 
                        PostDto.class))).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastpage(pagePost.isLast());

        return postResponse;
    }


    @Override
    public PostResponse searchPosts(Integer pageNumber, Integer pageSize, String keyword) {
        Pageable p = PageRequest.of(pageNumber, pageSize);


        Page<Post> pagePost = this.postRepo.findByTitleContaining(keyword, p);
        
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream().map((post -> 
                        this.modelMapper.map(post, 
                        PostDto.class))).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastpage(pagePost.isLast());

        return postResponse;
    }
    
}
