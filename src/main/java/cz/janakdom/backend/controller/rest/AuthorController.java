package cz.janakdom.backend.controller.rest;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.database.Author;
import cz.janakdom.backend.model.dto.AuthorDto;
import cz.janakdom.backend.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    public ApiResponse<Author> saveAuthor(@RequestBody AuthorDto author){

        if(authorService.findByName(author.getFirstname(), author.getSurname()) != null){
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS",null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", authorService.save(author));
    }

    @GetMapping("/")
    public ApiResponse<Page<Author>> listAuthor(Pageable pageable){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", authorService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<Author> getOneAuthor(@PathVariable int id){
        Author author = authorService.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), author == null ? "NOT-EXISTS" : "SUCCESS", author);
    }

    @PutMapping("/{id}")
    public ApiResponse<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto, @PathVariable int id) {
        if(!authorService.canBeUpdate(id, authorDto.getFirstname(), authorDto.getSurname())){
            return new ApiResponse<>(HttpStatus.OK.value(), "ALREADY-EXISTS", null);
        }

        AuthorDto author = authorService.update(id, authorDto);
        return new ApiResponse<>(HttpStatus.OK.value(), author==null ? "NOT-FOUND" : "SUCCESS", author);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAuthor(@PathVariable int id) {
        Author author = authorService.findById(id);

        if(author != null){
            if(author.getQuotes().size() == 0){
                authorService.delete(id);
            }
            else {
                return new ApiResponse<Void>(HttpStatus.OK.value(), "CANNOT-BE-DELETE", null);
            }
        }
        return new ApiResponse<Void>(HttpStatus.OK.value(), author == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}