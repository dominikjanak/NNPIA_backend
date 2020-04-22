package cz.janakdom.controller.rest;

import cz.janakdom.model.AuthRequest;
import cz.janakdom.util.JwtUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/authenticate")
public class AuthenticateController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Logger log;

    @PostMapping("/")
    @CrossOrigin
    public String login(@RequestBody AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        return doAuthenticate(authRequest.getUsername(), authRequest.getPassword(), request, response);
    }

    @GetMapping("/")
    @CrossOrigin
    public String testLoginViaGet(@RequestParam String username, @RequestParam String password, HttpServletRequest request, HttpServletResponse response) {
        return "{\"response\":\""+( doAuthenticate(username, password, request, response).contains("INVALID-CREDENTIALS") ? "FAIL" : "OK")+"\"}";
    }

    private String doAuthenticate(String username, String password, HttpServletRequest req, HttpServletResponse response){
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(token);
        } catch (Exception ex) {
            StringBuilder builder = new StringBuilder();

            builder.append("Invalid Credentials: ")
                    .append("ip: ").append(req.getRemoteAddr()).append(", ")
                    .append("requiest: ").append(req.getRequestURI()).append(", ")
                    .append("data: {")
                        .append("username: ").append(username).append(", ")
                        .append("password: ").append(password).append("};");

            log.warn(builder.toString());
            return "{\"response\":\"INVALID-CREDENTIALS\"}";
        }
        return "{\"response\":\"OK\", \"token\":\"" +  jwtUtil.generateToken(username) + "\"}";
    }
}