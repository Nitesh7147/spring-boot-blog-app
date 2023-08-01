package com.blog.app.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

	private int commentId;
	private String commentContent;
	private int userId;
	private int postId;
	private String userName;
}
