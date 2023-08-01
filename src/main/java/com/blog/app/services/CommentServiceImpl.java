package com.blog.app.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entities.Comment;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payload.CommentDto;
import com.blog.app.repositories.CommentRespository;
import com.blog.app.repositories.PostRepository;
import com.blog.app.repositories.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRespository commentRespository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given id is not found on server: " + userId));
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post with given id is not found on server: " + postId));
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setUser(user);
		comment.setPost(post);
		Comment comm = commentRespository.save(comment);
		return modelMapper.map(comm, CommentDto.class);
	}

}
