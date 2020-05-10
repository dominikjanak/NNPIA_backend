package cz.janakdom.controller.rest.security;

import cz.janakdom.model.ApiResponse;
import cz.janakdom.model.AuthToken;
import cz.janakdom.model.database.Author;
import cz.janakdom.model.database.User;
import cz.janakdom.model.dto.AuthorDto;
import cz.janakdom.model.dto.UserDto;
import cz.janakdom.service.AuthorService;
import cz.janakdom.service.UserService;
import cz.janakdom.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
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
    public ApiResponse<Author> save(@RequestBody AuthorDto author){

        if(authorService.findByName(author.getFirstname(), author.getSurname()) != null){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS",null);
        }

        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", authorService.save(author));
    }

    @GetMapping("/")
    public ApiResponse<List<Author>> list(){
        return new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", authorService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Author> getOne(@PathVariable int id){
        Author author = authorService.findById(id);
        return new ApiResponse<>(HttpStatus.OK.value(), author == null ? "NOT-EXISTS" : "SUCCESS", author);
    }

    @PutMapping("/{id}")
    public ApiResponse<AuthorDto> update(@RequestBody AuthorDto authorDto, @PathVariable int id) {
        AuthorDto author = authorService.update(id, authorDto);
        return new ApiResponse<>(HttpStatus.OK.value(), author==null ? "NOT-FOUND" : "SUCCESS", author);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        Author author = authorService.findById(id);
        if(author != null) authorService.delete(id);
        return new ApiResponse<Void>(HttpStatus.NOT_ACCEPTABLE.value(), author == null ? "NOT-FOUND" : "SUCCESS", null);
    }
}