package com.autodoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autodoc.entity.Persona;
import com.autodoc.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository repo;

    @Override
    public List<Persona> listar() {
        return repo.findAll();
    }

    @Override
    public Persona guardar(Persona persona) {
        return repo.save(persona);
    }

    @Override
    public Persona buscar(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
