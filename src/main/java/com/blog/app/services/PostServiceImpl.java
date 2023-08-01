 package com.blog.app.services;

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

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;
import com.blog.app.repositories.CategoryRepository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.repositories.UserRepository;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server: " + userId));
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with given id is not found on server: " + categoryId));		
		Post post = modelMapper.map(postDto, Post.class);
			post.setAddedDate(new Date());
			post.setUser(user);
			post.setCategory(category);
		Post newPost = postRepository.save(post);
		PostDto newPostDto = modelMapper.map(newPost, PostDto.class);
		return newPostDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with given id is not found on server: " + postId));
		post.setContent(postDto.getContent());
		post.setAddedDate(new Date());
		Post post1 = postRepository.save(post);
		return modelMapper.map(post1, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with given id is not found on server: " + postId));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPosts(int pageNumber, int pageSize , String sortBy, String sortDir) {
		//List<Post> allPosts = postRepository.findAll();
		// pageNumber starts from 0 page 
		Sort sort = null;
		if (sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		// pagination - findAll its a object of pageable
		Page<Post> pagePosts = postRepository.findAll(p);
		List<Post> allPosts = pagePosts.getContent();
		List<PostDto> dtoList = allPosts.stream().map( post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = PostResponse.builder().content(dtoList).pageNumber(pagePosts.getNumber()).pageSize(pagePosts.getSize())
					.totalElements(pagePosts.getTotalElements()).totalPages(pagePosts.getTotalPages()).lastPage(pagePosts.isLast()).build();
		return postResponse;
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with given id is not found on server: " + postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostsByCategory(int categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with given id is not found on server: " + categoryId));
		List<Post> list = postRepository.findByCategory(category);
		List<PostDto> listDTO = list.stream().map((posts) -> modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return listDTO;
	}

	@Override
	public List<PostDto> getAllPostsByUser(int userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server: " + userId));
		List<Post> list = postRepository.findByUser(user);
		List<PostDto> listDTO = list.stream().map((posts) -> modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		return listDTO;
	}

	@Override
	public List<PostDto> searchByTitle(String title) {
		List<Post> posts = this.postRepository.findByTitleContaining(title);	
		List<PostDto> dtoPosts = posts.stream().map( post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return dtoPosts;
	}

}
