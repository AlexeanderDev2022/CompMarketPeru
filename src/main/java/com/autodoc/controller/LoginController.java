package com.autodoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app")
public class LoginController {

    @GetMapping("")
    public String inicio() {
        return "redirect:/app/dashboard"; 
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
        }
        if (logout != null) {
            model.addAttribute("mensaje", "Sesión cerrada correctamente.");
        }
        return "login";	
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
