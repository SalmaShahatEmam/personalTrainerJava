package org.example.controller;


import jakarta.validation.Valid;
import org.example.Dto.CategoryRequestDTO;
import org.example.Dto.CateogryDTO;
import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryRepository category_repository;


    @GetMapping
    public ResponseEntity<List<Category>> index()
    {
        List<Category> categories =  category_repository.findAll();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/show_category/{id}")
    public Category showCategory(@PathVariable("id") Long id) {
     return  category_repository.findById(id).get();

    }

    @GetMapping("search-category/{search-key}")
    public List<CateogryDTO> searchByTitle(@PathVariable("search-key") String Key)
    {
        List<Category> categories = category_repository.findAll();

        return categories.stream().
                filter(category -> category.getTitle_ar()
                        .equalsIgnoreCase(Key) || category.getTitle_en()
                        .equalsIgnoreCase(Key) ).map(category -> new CateogryDTO(category))

                        .collect(Collectors.toList());
    }


    @PostMapping
    public CateogryDTO store(@Valid @RequestBody CategoryRequestDTO request){
        Category category = new Category();
        category.setTitle_en(request.getTitle_en());
        category.setTitle_ar(request.getTitle_ar());
        category.setIcon(request.getIcon());
        category.setActive(request.getActive());
        category.setPayed(request.getPayed());
        Category new_category =  category_repository.save(category);

        return new CateogryDTO(new_category);
    }


    @PutMapping("/{id}")
    public CateogryDTO Update(@PathVariable("id") Long id , @Valid @RequestBody CategoryRequestDTO request)
    {
        Category category = category_repository.getById(id);

        category.setTitle_en(request.getTitle_en());
        category.setTitle_ar(request.getTitle_ar());
        category.setIcon(request.getIcon());
        category.setActive(request.getActive());
        category.setPayed(request.getPayed());
        Category new_category =  category_repository.save(category);

        return new CateogryDTO(new_category);

    }

    @PostMapping("/addImage/{id}")
    public String addImage(@PathVariable("id") Long id , @RequestPart("file") MultipartFile file)
    {
        Category category = category_repository.getById(id);

        try {
            category.setImg_name(file.getOriginalFilename());
            category.setImg_type(file.getContentType());
            category.setImg_data(file.getBytes());
            category_repository.save(category);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "done";
    }
    @DeleteMapping("/{id}")
    public String Delete(@PathVariable("id") Long id)
    {
        category_repository.deleteById(id);

        return "deleted successfully";
    }

    @GetMapping("category-image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id)
    {
        Category category = category_repository.getById(id);

        byte[] imageData = category.getImg_data();

        return ResponseEntity.ok().contentType(MediaType.valueOf(category.getImg_type())).body(imageData);
    }
}
