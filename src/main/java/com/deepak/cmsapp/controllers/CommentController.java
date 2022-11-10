package com.deepak.cmsapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.cmsapp.payloads.ApiResponse;
import com.deepak.cmsapp.payloads.CommentDto;
import com.deepak.cmsapp.services.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                         @PathVariable Integer postId, @PathVariable Integer userId){
            
        CommentDto createdComment = this.commentService.createComment(commentDto, postId, userId);
            
        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);

    }

    @DeleteMapping("/comment/{commentId}/delete")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully !!", true), HttpStatus.OK);
    }
}
