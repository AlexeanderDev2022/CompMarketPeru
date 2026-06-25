package com.autodoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autodoc.service.ProductoService;

/**
 * Catálogo público de productos: vitrina digital accesible sin autenticarse,
 * tal como lo describe el informe en el "Módulo de Exhibición de Productos"
 * (2.2 Alcance).
 */
@Controller
public class CatalogoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        model.addAttribute("productos", productoService.listar());
        return "catalogo";
    }
}
