package com.autodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autodoc.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{

}
