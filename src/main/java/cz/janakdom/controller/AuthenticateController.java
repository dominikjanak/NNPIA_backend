package cz.janakdom.controller;

import cz.janakdom.model.AuthRequest;
import cz.janakdom.util.JwtUtil;
import lombok.var;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collector;

@RestController
public class AuthenticateController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Logger log;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest, HttpServletResponse responses, HttpServletRequest request) {
        return doAuthenticate(authRequest.getUsername(), authRequest.getPassword(), responses, request);
    }

    @Profile("local")
    @GetMapping("/authenticate")
    public String generateToken(@RequestParam String username, @RequestParam String password, HttpServletResponse responses, HttpServletRequest request) {
        return doAuthenticate(username, password, responses, request);
    }

    private String doAuthenticate(String username, String password, HttpServletResponse res, HttpServletRequest req){
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

        res.setStatus(HttpServletResponse.SC_OK);
        return jwtUtil.generateToken(username);
    }
}