package com.hormigo.david.parkingmanager.core.adapter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
    @GetMapping("/home")
    public String getHome(){
        return "home";
    }
    
}
