package com.deepak.cmsapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.cmsapp.entities.Comment;
import com.deepak.cmsapp.entities.Post;
import com.deepak.cmsapp.entities.CustomUser;
import com.deepak.cmsapp.exceptions.ResourceNotFoundException;
import com.deepak.cmsapp.payloads.CommentDto;
import com.deepak.cmsapp.repositories.CommentRepo;
import com.deepak.cmsapp.repositories.PostRepo;
import com.deepak.cmsapp.repositories.UserRepo;
import com.deepak.cmsapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post", " Post ID ", postId));

        CustomUser customUser = this.userRepo.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", " User ID ", userId));
        
        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setCustomUser(customUser);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", " Comment ID ", commentId));

        this.commentRepo.delete(comment);
        
    }
    
}
