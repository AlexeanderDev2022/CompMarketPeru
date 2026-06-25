package com.autodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autodoc.entity.CierreCaja;

public interface CierreCajaRepository
        extends JpaRepository<CierreCaja, String> {

}