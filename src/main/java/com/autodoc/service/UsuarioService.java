package com.autodoc.service;

import java.util.List;
import com.autodoc.entity.Usuario;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario guardar(Usuario usuario);

    Usuario buscar(Integer id);

    void eliminar(Integer id);
}