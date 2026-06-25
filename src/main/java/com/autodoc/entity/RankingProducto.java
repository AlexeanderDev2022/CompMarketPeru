package com.autodoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "v_ranking_productos")
public class RankingProducto {

    @Id
    private Integer id_producto;

    private String nombre;
    private String marca;
    private Integer unidades_vendidas;
    private Double ingresos_totales;

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getUnidades_vendidas() {
        return unidades_vendidas;
    }

    public void setUnidades_vendidas(Integer unidades_vendidas) {
        this.unidades_vendidas = unidades_vendidas;
    }

    public Double getIngresos_totales() {
        return ingresos_totales;
    }

    public void setIngresos_totales(Double ingresos_totales) {
        this.ingresos_totales = ingresos_totales;
    }
}