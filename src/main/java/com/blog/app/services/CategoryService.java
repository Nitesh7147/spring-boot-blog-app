package com.blog.app.services;

import java.util.List;

import com.blog.app.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
	public void deleteCategory(int categoryId);
	// interface ke andar by default methods public ho jate hai
	CategoryDto getCategory(int categoryId);
	List<CategoryDto> getAll();
	
}
