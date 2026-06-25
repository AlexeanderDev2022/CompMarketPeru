package com.autodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autodoc.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
}
