package cz.janakdom.backend.controller.rest.security;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.AuthToken;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.model.dto.UserDto;
import cz.janakdom.backend.security.JwtUtil;
import cz.janakdom.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/security")
public class RegisterController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/register")
    public ApiResponse<AuthToken> register(@RequestBody UserDto user) {

        if(user.getUsername().length() <= 3
            || user.getSurname().isEmpty()
            || user.getFirstname().isEmpty()
            || user.getPassword().length() <= 6
            || !isValidEmail(user.getEmail())
        ){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID",null);
        }

        if(!userService.isEmailUnique(user.getEmail())){
            return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "EMAIL-ALREADY-USED",null);
        }

        User findUser = userService.findByUsername(user.getUsername());

        if (findUser == null){
            User persisted = userService.save(user);
            final String token = jwtUtil.generateToken(persisted.getUsername());
            return new ApiResponse<>(200, "SUCCESS",new AuthToken(persisted.getUsername(), token));
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS",null);
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}