package com.autodoc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "v_cierre_caja_hoy")
public class CierreCaja {

    @Id
    private String tipo_comprobante;

    private Integer cantidad_ventas;
    private Double total_subtotal;
    private Double total_igv;
    private Double total_general;

    public String getTipo_comprobante() {
        return tipo_comprobante;
    }

    public void setTipo_comprobante(String tipo_comprobante) {
        this.tipo_comprobante = tipo_comprobante;
    }

    public Integer getCantidad_ventas() {
        return cantidad_ventas;
    }

    public void setCantidad_ventas(Integer cantidad_ventas) {
        this.cantidad_ventas = cantidad_ventas;
    }

    public Double getTotal_subtotal() {
        return total_subtotal;
    }

    public void setTotal_subtotal(Double total_subtotal) {
        this.total_subtotal = total_subtotal;
    }

    public Double getTotal_igv() {
        return total_igv;
    }

    public void setTotal_igv(Double total_igv) {
        this.total_igv = total_igv;
    }

    public Double getTotal_general() {
        return total_general;
    }

    public void setTotal_general(Double total_general) {
        this.total_general = total_general;
    }
}