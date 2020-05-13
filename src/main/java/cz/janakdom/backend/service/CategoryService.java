package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.CategoryDao;
import cz.janakdom.backend.model.database.Category;
import cz.janakdom.backend.model.dto.CategoryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service(value = "categoryService")
public class CategoryService {

    private final CategoryDao dao;

    public CategoryService(CategoryDao dao) {
        this.dao = dao;
    }

    public Page<Category> findAll(Pageable pageable){
        return dao.findAll(pageable);
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
