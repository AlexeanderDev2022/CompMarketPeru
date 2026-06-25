package com.autodoc.service;

import java.util.List;
import com.autodoc.entity.Categoria;

public interface CategoriaService {

    List<Categoria> listar();

    Categoria guardar(Categoria categoria);

    Categoria buscar(Integer id);

    void eliminar(Integer id);
}
