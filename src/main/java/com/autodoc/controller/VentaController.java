package com.autodoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.autodoc.entity.Usuario;
import com.autodoc.entity.Venta;
import com.autodoc.repository.UsuarioRepository;
import com.autodoc.service.ClienteService;
import com.autodoc.service.ProductoService;
import com.autodoc.service.VentaService;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listar());
        return "ventas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("productos", productoService.listar());
        return "ventas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta, Authentication authentication, Model model) {

        // El vendedor que registra la venta es el usuario autenticado, no un campo del formulario.
        Usuario usuario = usuarioRepository.findByUsername(authentication.getName()).orElse(null);
        venta.setUsuario(usuario);

        try {
            ventaService.guardar(venta);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("venta", venta);
            model.addAttribute("clientes", clienteService.listar());
            model.addAttribute("productos", productoService.listar());
            model.addAttribute("error", ex.getMessage());
            return "ventas/form";
        }

        return "redirect:/ventas";
    }
}
