package com.deepak.cmsapp.services;

import com.deepak.cmsapp.payloads.CommentDto;

public interface CommentService {
    
    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
    void deleteComment(Integer commentId);

}
