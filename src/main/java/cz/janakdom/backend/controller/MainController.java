package cz.janakdom.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RequestMapping(value = "/")
@Controller
public class MainController {
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
