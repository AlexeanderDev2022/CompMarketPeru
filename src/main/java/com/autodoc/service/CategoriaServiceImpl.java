package com.autodoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autodoc.entity.Categoria;
import com.autodoc.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    @Override
    public List<Categoria> listar() {
        return repo.findAll();
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        return repo.save(categoria);
    }

    @Override
    public Categoria buscar(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}