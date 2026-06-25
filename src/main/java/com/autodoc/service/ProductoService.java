package com.autodoc.service;

import java.util.List;
import com.autodoc.entity.Producto;

public interface ProductoService {

    List<Producto> listar();

    Producto guardar(Producto producto);

    Producto buscar(Integer id);

    void eliminar(Integer id);
}