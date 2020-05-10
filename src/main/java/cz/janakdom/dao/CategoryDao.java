package cz.janakdom.dao;

import cz.janakdom.model.database.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category,Integer> {
    Category findByName(String name);
}
