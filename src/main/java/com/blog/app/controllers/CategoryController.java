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
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payload.ApiResponse;
import com.blog.app.payload.CategoryDto;
import com.blog.app.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/add")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
	}

	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
			@PathVariable int categoryId) {
		CategoryDto cdto = categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(cdto);
	}

	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok(new ApiResponse("Category Deleted Successfully", true, HttpStatus.OK));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAll(){
		List<CategoryDto> list = categoryService.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable int categoryId){
		CategoryDto cat = categoryService.getCategory(categoryId);
		return ResponseEntity.ok(cat);
	}
}
