package com.autodoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autodoc.entity.Rol;
import com.autodoc.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository repo;

    @Override
    public List<Rol> listar() {
        return repo.findAll();
    }

    @Override
    public Rol guardar(Rol rol) {
        return repo.save(rol);
    }

    @Override
    public Rol buscar(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}