package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.AuthorDao;
import cz.janakdom.backend.model.database.Author;
import cz.janakdom.backend.model.dto.AuthorDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "authorService")
public class AuthorService {

    private final AuthorDao dao;

    @Autowired
    public AuthorService(AuthorDao dao) {
        this.dao = dao;
    }

    public Page<Author> findAll(Pageable pageable){
        return dao.findAll(pageable);
    }

    public Author findById(int id) {
        Optional<Author> optional = dao.findById(id);
        return optional.orElse(null);
    }

    public Author findByName(String firstname, String surname) {
        Optional<Author> optional = Optional.ofNullable(dao.findByFirstnameAndSurname(firstname, surname));
        return optional.orElse(null);
    }

    public boolean authorExists(String firstname, String surname) {
        return findByName(firstname, surname) != null;
    }

    public void delete(int id) {
        dao.deleteById(id);
    }

    public AuthorDto update(Integer id, AuthorDto authorDto) {
        Author author = findById(id);
        if(author != null) {
            BeanUtils.copyProperties(authorDto, author, "id");
            dao.save(author);
            return authorDto;
        }
        return null;
    }

    public Author save(AuthorDto author) {
        Author newAuthor = new Author();
        BeanUtils.copyProperties(author, newAuthor);
        return dao.save(newAuthor);
    }

    public boolean canBeUpdate(int id, String firstname, String surname) {
        Author author = findByName(firstname, surname);
        if(author == null) return true;
        return author.getId() == id;
    }
}
