package com.autodoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.autodoc.service.ReporteService;

@Controller
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/reportes/stock-bajo")
    public String stockBajo(Model model) {
        model.addAttribute("productos",
                reporteService.obtenerStockBajo());

        return "reportes/stock-bajo";
    }

    @GetMapping("/reportes/ranking")
    public String ranking(Model model) {
        model.addAttribute("ranking",
                reporteService.obtenerRanking());

        return "reportes/ranking";
    }

    @GetMapping("/reportes/cierre-caja")
    public String cierreCaja(Model model) {
        model.addAttribute("cierre",
                reporteService.obtenerCierreCaja());

        return "reportes/cierre-caja";
    }
}