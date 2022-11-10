package com.deepak.cmsapp.controllers;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deepak.cmsapp.enums.SortingOrder;
import com.deepak.cmsapp.payloads.PostDto;
import com.deepak.cmsapp.payloads.PostResponse;
import com.deepak.cmsapp.services.FileService;
import com.deepak.cmsapp.services.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Operation(summary ="Create Post", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId)
    {
        PostDto post = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
    }


    @GetMapping("/user/{userId}/posts")
    @Operation(summary ="Get Post By User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PostResponse> getPostsByUser(
        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
        @PathVariable Integer userId)
    {

        PostResponse posts = this.postService.getPostsByUser(pageNumber, pageSize, userId);
        return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);

    }

    @GetMapping("/category/{categoryId}/posts")
    @Operation(summary ="Get Post By Category", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<PostResponse> getPostsByCategory(
        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
        @PathVariable Integer categoryId)
    {

        PostResponse posts = this.postService.getPostsByCategory(pageNumber, pageSize, categoryId);
        return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);

    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
        @RequestParam(value = "sortOrder", defaultValue = "ASC", required = false) SortingOrder sortOrder
    )
    {

        PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);

    }

    @Operation(summary ="Get Post By Id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(
        @PathVariable Integer postId)
    {

        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);

    }

    @Operation(summary ="Delete Post By Category", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePostById(
        @PathVariable Integer postId)
    {

        this.postService.deletePost(postId);
        return new ResponseEntity<String>("post deleted sucessfully", HttpStatus.OK);

    }
    @Operation(summary ="Update Post By Category", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable Integer postId)
    {
        PostDto post = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    @Operation(summary ="Search Post", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/posts/search")
    public ResponseEntity<PostResponse> searchPosts(
        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
        @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword)
    {
        PostResponse postResponse = this.postService.searchPosts(pageNumber, pageSize, keyword);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @Operation(summary ="Post Image Upload", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                            @PathVariable Integer postId) throws IOException{

        String fileName = this.fileService.uploadImage(path, image);
        PostDto postDto = this.postService.getPostById(postId);
        postDto.setImageName(fileName);

        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);


    }
    @Operation(summary ="Get post image", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
