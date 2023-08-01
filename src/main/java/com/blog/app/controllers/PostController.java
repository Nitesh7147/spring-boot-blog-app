package com.blog.app.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.config.AppConstants;
import com.blog.app.payload.ApiResponse;
import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;
import com.blog.app.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/add-post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId){
		PostDto createdPost = 	this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable int postId){
		PostDto postDTO = postService.updatePost(postDto, postId);
		return ResponseEntity.ok(postDTO);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> allPostByCategory(@PathVariable int categoryId){
		List<PostDto> list = postService.getAllPostsByCategory(categoryId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> allPostByUser(@PathVariable int userId){
		List<PostDto> list = postService.getAllPostsByUser(userId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostByPostId(@PathVariable int postId){
		PostDto post = postService.getPostById(postId);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping("/all-posts")
	public ResponseEntity<PostResponse> allPost(@RequestParam(value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER, required=false) int pageNumber,
			                                    @RequestParam(value="pageSize", defaultValue=AppConstants.PAGE_SIZE, required=false) int pageSize,
	                                            @RequestParam(value="sortBy", defaultValue=AppConstants.SORT_BY, required=false) String sortBy,
	                                            @RequestParam(value="sortDir", defaultValue=AppConstants.SORT_DIR, required=false) String sortDir){
		PostResponse postList = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postList);
	}
	
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable int postId){
		postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Post Deleted Successfully", true, HttpStatus.OK));
	}
	
	// search by title
	@GetMapping("/search/{title}")
	public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String title){
		List<PostDto> list = postService.searchByTitle(title);
		return ResponseEntity.ok(list);
	}
	
}
