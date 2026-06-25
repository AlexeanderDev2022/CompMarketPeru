package com.autodoc.service;

import java.util.List;

import com.autodoc.entity.Cliente;

public interface ClienteService {

    List<Cliente> listar();

    Cliente guardar(Cliente cliente);

    Cliente buscar(Integer id);

    void eliminar(Integer id);
}