package com.blog.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entities.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payload.CategoryDto;
import com.blog.app.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = modelMapper.map(categoryDto, Category.class);
		Category savedCat = categoryRepository.save(cat);
		CategoryDto	dtoSavedCat = modelMapper.map(savedCat, CategoryDto.class);
		return dtoSavedCat;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category cat = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new ResourceNotFoundException("Category with given id is not found on server: " + categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category cat1 = categoryRepository.save(cat);
		CategoryDto dtoCategory = modelMapper.map(cat1, CategoryDto.class);
		return dtoCategory;
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with given id is not found on server: " + categoryId));
			categoryRepository.delete(cat);
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with given id is not found on server: " + categoryId));
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAll() {
		List<Category> listCategory = categoryRepository.findAll();
		List<CategoryDto> listDto = listCategory.stream().map(listCat -> modelMapper.map(listCat, CategoryDto.class)).collect(Collectors.toList());
		return listDto;
	}
	
}
