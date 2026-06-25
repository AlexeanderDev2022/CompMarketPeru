package com.autodoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.autodoc.entity.Rol;
import com.autodoc.service.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("roles", service.listar());
        return "roles/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Rol rol) {
        service.guardar(rol);
        return "redirect:/roles";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("rol", service.buscar(id));
        return "roles/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/roles";
    }
}
