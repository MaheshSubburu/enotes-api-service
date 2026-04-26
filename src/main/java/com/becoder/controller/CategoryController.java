package com.becoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.becoder.dto.CategoryDto;
import com.becoder.dto.CategoryResponse;
import com.becoder.service.CategoryService;
import com.becoder.util.CommonUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
		
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		if(saveCategory) return CommonUtil.createBuildResponseMessage("save success", HttpStatus.CREATED);
		 
		//return new ResponseEntity<>("saved successfully.!", HttpStatus.CREATED);
		
		else return CommonUtil.createErrorResponseMessage("Category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory() {
		
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) 
			return ResponseEntity.noContent().build();
		
		else return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
			// return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategory() {
		
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) 
			return ResponseEntity.noContent().build();
		
		else return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
			// return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception {

//	    try {
//	    	CategoryDto categoryDto = categoryService.getCategoryDetailsById(id);
//
//		    if (ObjectUtils.isEmpty(categoryDto)) {
//		        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//		                .body("Category not found with id: " + id);
//		    }
//
//		    return ResponseEntity.ok(categoryDto);
//	    } catch (ResourceNotFoundException re) {
//	    	log.error("Conroller :: getCategoryDetailsById ::", re.getMessage());
//	    	return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
//		}
//	    catch (Exception e) {
//	    	return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	    
	    CategoryDto categoryDto = categoryService.getCategoryDetailsById(id);

	    if (ObjectUtils.isEmpty(categoryDto)) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Category not found");
	    }

	    return ResponseEntity.ok(categoryDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {

	    Boolean deleted = categoryService.deleteCategoryById(id);

	    if (deleted) {
	        return ResponseEntity.status(HttpStatus.OK)
	                .body("Category deleted successfully.!");
	    }

	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Category not deleted");
	}
}