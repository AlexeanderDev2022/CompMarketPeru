package com.autodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autodoc.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
