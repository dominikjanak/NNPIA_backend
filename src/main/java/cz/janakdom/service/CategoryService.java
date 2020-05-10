package cz.janakdom.service;

import cz.janakdom.dao.CategoryDao;
import cz.janakdom.model.database.Category;
import cz.janakdom.model.dto.CategoryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "categoryService")
public class CategoryService {

    private final CategoryDao dao;

    public CategoryService(CategoryDao dao) {
        this.dao = dao;
    }

    public List<Category> findAll(){
        return dao.findAll();
    }

    public Category findById(int id) {
        Optional<Category> optional = dao.findById(id);
        return optional.orElse(null);
    }

    public Category findByName(String name) {
        Optional<Category> optional = Optional.ofNullable(dao.findByName(name));
        return optional.orElse(null);
    }

    public boolean categoryExists(String name) {
        return findByName(name) != null;
    }

    public void delete(int id) {
        dao.deleteById(id);
    }

    public CategoryDto update(Integer id, CategoryDto categoryDto) {
        Category category = findById(id);
        if(category != null) {
            BeanUtils.copyProperties(categoryDto, category, "id");
            dao.save(category);
            return categoryDto;
        }
        return null;
    }

    public Category save(CategoryDto category) {
        Category newCategory = new Category();
        BeanUtils.copyProperties(category, newCategory);
        return dao.save(newCategory);
    }

    public boolean canBeUpdate(int id, String name){
        Category category = findByName(name);
        if(category == null) return true;
        return category.getId() == id;
    }
}
