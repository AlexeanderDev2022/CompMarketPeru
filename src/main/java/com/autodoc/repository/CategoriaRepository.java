package com.autodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autodoc.entity.Categoria;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Integer> {
}