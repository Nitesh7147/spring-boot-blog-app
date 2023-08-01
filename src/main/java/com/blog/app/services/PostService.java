package com.blog.app.services;

import java.util.List;

import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto, int userId, int categoryId);
	PostDto updatePost(PostDto postDto, int postId);
	void deletePost(int postId);
	PostResponse getAllPosts(int pageSize, int pageNumber, String sortBy, String sortDir);
	PostDto getPostById(int postId);
	List<PostDto> getAllPostsByCategory(int categoryId);
	List<PostDto> getAllPostsByUser(int userId);
	
	List<PostDto> searchByTitle(String title);
	
	
}
 