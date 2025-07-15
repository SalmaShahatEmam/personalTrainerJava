package org.example.services;

import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class categoryService {

    @Autowired
    public  CategoryRepository categoryRepository;

    public List<Category> getCategories()
    {
        return categoryRepository.findAll();
    }



}
