package com.autodoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autodoc.entity.CierreCaja;
import com.autodoc.entity.RankingProducto;
import com.autodoc.entity.StockBajo;
import com.autodoc.repository.CierreCajaRepository;
import com.autodoc.repository.RankingProductoRepository;
import com.autodoc.repository.StockBajoRepository;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private StockBajoRepository stockRepo;

    @Autowired
    private RankingProductoRepository rankingRepo;

    @Autowired
    private CierreCajaRepository cierreRepo;

    @Override
    public List<StockBajo> obtenerStockBajo() {
        return stockRepo.findAll();
    }

    @Override
    public List<RankingProducto> obtenerRanking() {
        return rankingRepo.findAll();
    }

    @Override
    public List<CierreCaja> obtenerCierreCaja() {
        return cierreRepo.findAll();
    }
}