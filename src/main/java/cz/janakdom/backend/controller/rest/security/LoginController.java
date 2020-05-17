package cz.janakdom.backend.controller.rest.security;

import cz.janakdom.backend.model.ApiResponse;
import cz.janakdom.backend.model.AuthRequest;
import cz.janakdom.backend.model.AuthToken;
import cz.janakdom.backend.model.database.User;
import cz.janakdom.backend.security.JwtUtil;
import cz.janakdom.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/security")
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/login")
    public ApiResponse<AuthToken> authenticate(@RequestBody AuthRequest authRequest) throws AuthenticationException {

        User user = doAuthenticate(authRequest.getUsername(), authRequest.getPassword());

        if(user != null){
            final String token = jwtUtil.generateToken(user.getUsername());
            return new ApiResponse<>(200, "SUCCESS", new AuthToken(user.getUsername(), token));
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID-CREDENTIALS",null);
    }

    @GetMapping("/login")
    public ApiResponse<Boolean> testLoginViaGet(@RequestParam String username, @RequestParam String password) throws AuthenticationException {
        User user = doAuthenticate(username, password);

        if(user != null){
            final String token = jwtUtil.generateToken(user.getUsername());
            return new ApiResponse<>(200, "SUCCESS", true);
        }

        return new ApiResponse<>(HttpStatus.NOT_ACCEPTABLE.value(), "INVALID-CREDENTIALS",false);
    }

    @GetMapping("/logout")
    @PostMapping("/logout")
    public ApiResponse<Void> logout() throws AuthenticationException {
        return new ApiResponse<>(200, "success",null);
    }

    private User doAuthenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return userService.findByUsername(username);
        } catch (Exception ex) {
            log.warn("Invalid Credentials: \n  username: " + username + "\n  password: " + password);
        }
        return null;
    }
}