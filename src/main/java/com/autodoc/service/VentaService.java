package com.autodoc.service;

import java.util.List;

import com.autodoc.entity.Venta;

public interface VentaService {

    List<Venta> listar();

    Venta guardar(Venta venta);

    Venta buscar(Integer id);

    void eliminar(Integer id);

}
