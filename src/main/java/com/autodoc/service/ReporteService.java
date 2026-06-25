package com.autodoc.service;

import java.util.List;

import com.autodoc.entity.CierreCaja;
import com.autodoc.entity.RankingProducto;
import com.autodoc.entity.StockBajo;

public interface ReporteService {

    List<StockBajo> obtenerStockBajo();

    List<RankingProducto> obtenerRanking();

    List<CierreCaja> obtenerCierreCaja();
}