package cz.janakdom.dao;

import cz.janakdom.model.database.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category,Integer> {
}
