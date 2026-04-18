package com.becoder.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.entity.Category;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
		
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
		
		Category category = modelMapper.map(categoryDto, Category.class);
		
		categoryDto.setIsDeleted(false);
		categoryDto.setCreatedBy(1);
		
		Category saveCategory = categoryRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) return false;
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findByIsDeletedFalse();
		
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).toList();
		return categoryDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		
		List<CategoryResponse> categoryResponseList = 
				categories.stream().map(cat -> modelMapper.map(cat, CategoryResponse.class)).toList();
		
		return categoryResponseList;
	}

	@Override
	public CategoryDto getCategoryDetailsById(Integer id) throws Exception {
		
		Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id: "+ id));
		
		if(!ObjectUtils.isEmpty(category)) {
			if(category.getName() == null) {
				throw new IllegalArgumentException("Name is null");
			}
			return modelMapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategoryById(Integer id) {
		
        Optional<Category> findByCategory = categoryRepository.findById(id);
		
		if(findByCategory.isPresent()) {
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepository.save(category);
			return true;
		}
		return false;
	}
	
	

}