package cz.janakdom.backend.dao;


import cz.janakdom.backend.model.database.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
