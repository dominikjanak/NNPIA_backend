package cz.janakdom.backend.dao;

import cz.janakdom.backend.model.database.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorDao extends JpaRepository<Author, Integer> {
    Author findByFirstnameAndSurname(String firstname, String surname);
}
