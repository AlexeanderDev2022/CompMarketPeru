package com.autodoc.service;

import java.util.List;
import com.autodoc.entity.Persona;

public interface PersonaService {

    List<Persona> listar();

    Persona guardar(Persona persona);

    Persona buscar(Integer id);

    void eliminar(Integer id);
}
