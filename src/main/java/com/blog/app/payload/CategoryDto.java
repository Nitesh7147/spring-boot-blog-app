package com.blog.app.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class CategoryDto {
	
	private int categoryId;
	private String categoryTitle;
	private String categoryDescription;
}
