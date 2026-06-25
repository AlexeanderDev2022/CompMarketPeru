package com.autodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.autodoc.entity.RankingProducto;

public interface RankingProductoRepository
        extends JpaRepository<RankingProducto, Integer> {

}