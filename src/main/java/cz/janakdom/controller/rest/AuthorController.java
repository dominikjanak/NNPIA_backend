package cz.janakdom.controller.rest;

import cz.janakdom.model.ApiResponse;
import cz.janakdom.model.database.Author;
import cz.janakdom.model.dto.AuthorDto;
import cz.janakdom.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS",null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", authorService.save(author));
    }

    @GetMapping("/")
    public ApiResponse<List<Author>> listAuthor(){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", authorService.findAll());
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
        if(author != null) authorService.delete(id);
        return new ApiResponse<Void>(HttpStatus.NOT_ACCEPTABLE.value(), author == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}