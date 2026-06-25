package com.autodoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.autodoc.entity.Persona;
import com.autodoc.service.PersonaService;

@Controller
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("personas", service.listar());
        return "personas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("persona", new Persona());
        return "personas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Persona persona) {
        service.guardar(persona);
        return "redirect:/personas";
    }
}
