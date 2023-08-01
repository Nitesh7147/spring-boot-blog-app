package com.blog.app.services;

import com.blog.app.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,int postId, int userId);
}
