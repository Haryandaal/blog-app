package com.harr.blog.service.impl;

import com.harr.blog.dtos.CategoryDto;
import com.harr.blog.entity.Category;
import com.harr.blog.repository.CategoryRepository;
import com.harr.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category save = categoryRepository.save(category);
        return modelMapper.map(save, CategoryDto.class);
    }
}
