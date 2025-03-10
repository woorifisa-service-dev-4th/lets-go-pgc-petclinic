package dev.spring.petclinic.global.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String welcome() {
        return "index";
    }

    @GetMapping("/oups")
    public ModelAndView triggerException() {
        throw new RuntimeException("Expected: controller used to showcase what happens when an exception is thrown");
    }
}