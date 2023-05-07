package com.hormigo.david.parkingmanager.core;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.service.UserService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @GetMapping("/register")
    public String showUserCreateForm(Model model) {
        addRoleSelectList(model);
        UserDao userDao = new UserDao();
        model.addAttribute("userDao", userDao);
        return "user/createform";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDao") UserDao userDao,BindingResult bindingResult, Model model){
        // Si algún error de validación automática con UserDao        
        if (bindingResult.hasErrors()) {
            model.addAttribute("userDao",userDao);
            addRoleSelectList(model);
            return "user/createForm";
        }
        
        try {
            this.userService.register(userDao);
        }
        // Cuando ya existe un usuario con el correo
        catch (UserExistsException exception){
            model.addAttribute("userDao", userDao);
            addRoleSelectList(model);
            bindingResult.reject("email", "Ya existe el usuario con el correo");
            return "user/createform";
        }
        return "redirect:/";
    }

    private void addRoleSelectList(Model model) {
        List<Role> roles = Arrays.asList(Role.values());
        model.addAttribute("roles", roles);
    }
    
}
