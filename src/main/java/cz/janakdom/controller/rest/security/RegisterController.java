package cz.janakdom.controller.rest.security;

import cz.janakdom.model.ApiResponse;
import cz.janakdom.model.AuthToken;
import cz.janakdom.model.database.User;
import cz.janakdom.model.dto.UserDto;
import cz.janakdom.service.UserService;
import cz.janakdom.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = "/api/register")
public class RegisterController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/")
    public ApiResponse<AuthToken> register(@RequestBody UserDto user) {

        if(user.getUsername().length() <= 3
            || user.getLastname().isEmpty()
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
            return new ApiResponse<>(200, "SUCCESS",new AuthToken(persisted.getId(), persisted.getUsername(), token));
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "ALREADY-EXISTS",null);
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}