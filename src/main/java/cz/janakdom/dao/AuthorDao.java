package cz.janakdom.dao;

import cz.janakdom.model.database.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, Integer> {
}
