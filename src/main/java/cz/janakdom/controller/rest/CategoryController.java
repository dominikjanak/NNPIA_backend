package cz.janakdom.controller.rest;

import cz.janakdom.model.ApiResponse;
import cz.janakdom.model.database.Category;
import cz.janakdom.model.dto.CategoryDto;
import cz.janakdom.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    public ApiResponse<Category> saveCategory(@RequestBody CategoryDto category){

        if(categoryService.findByName(category.getName()) != null){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS",null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", categoryService.save(category));
    }

    @GetMapping("/")
    public ApiResponse<List<Category>> listCategory(){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> getOneCategory(@PathVariable int id){
        Category category = categoryService.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), category == null ? "NOT-EXISTS" : "SUCCESS", category);
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable int id) {
        if(!categoryService.canBeUpdate(id, categoryDto.getName())){
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS", null);
        }

        CategoryDto category = categoryService.update(id, categoryDto);
        return new ApiResponse<>(HttpStatus.OK.value(), category==null ? "NOT-FOUND" : "SUCCESS", category);

    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable int id) {
        Category category = categoryService.findById(id);
        if(category != null) categoryService.delete(id);
        return new ApiResponse<Void>(HttpStatus.NOT_ACCEPTABLE.value(), category == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}