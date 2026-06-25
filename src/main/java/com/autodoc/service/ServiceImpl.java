package com.autodoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autodoc.entity.Cliente;
import com.autodoc.repository.ClienteRepository;

@Service
public class ServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Override
    public List<Cliente> listar() {
        return repo.findAll();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    @Override
    public Cliente buscar(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}