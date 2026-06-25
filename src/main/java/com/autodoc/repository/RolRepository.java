package com.autodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autodoc.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}