package com.autodoc.service;

import java.util.List;
import com.autodoc.entity.Rol;

public interface RolService {

    List<Rol> listar();

    Rol guardar(Rol rol);

    Rol buscar(Integer id);

    void eliminar(Integer id);
}
