package com.autodoc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autodoc.entity.DetalleVenta;
import com.autodoc.entity.Producto;
import com.autodoc.entity.Venta;
import com.autodoc.repository.ProductoRepository;
import com.autodoc.repository.VentaRepository;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Override
    public List<Venta> listar() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta buscar(Integer id) {
        return ventaRepo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        ventaRepo.deleteById(id);
    }

    /**
     * Registra una venta completa de forma atómica: valida stock, calcula
     * subtotal/IGV(18%)/total, descuenta el stock de cada producto y guarda
     * la venta junto con su detalle. Si algo falla, Spring revierte toda la
     * operación (rollback) gracias a @Transactional, evitando que el stock
     * quede descontado sin que la venta se haya registrado.
     */
    @Override
    @Transactional
    public Venta guardar(Venta venta) {

        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un producto.");
        }

        double subtotal = 0;

        for (DetalleVenta d : venta.getDetalles()) {

            Producto p = productoRepo.findById(d.getProducto().getId_producto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));

            if (p.getStock() == null || p.getStock() < d.getCantidad()) {
                throw new IllegalStateException(
                        "Stock insuficiente para el producto: " + p.getNombre());
            }

            d.setVenta(venta);
            d.setPrecio_unit(p.getPrecio_venta());
            d.setSubtotal(d.getCantidad() * p.getPrecio_venta());

            subtotal += d.getSubtotal();

            p.setStock(p.getStock() - d.getCantidad());
            productoRepo.save(p);
        }

        if (venta.getSerie() == null || venta.getSerie().isBlank()) {
            venta.setSerie("B001");
        }
        if (venta.getTipo_comprobante() == null) {
            venta.setTipo_comprobante("BOLETA");
        }
        if (venta.getCorrelativo() == null) {
            venta.setCorrelativo((int) ventaRepo.count() + 1);
        }
        if (venta.getDescuento() == null) {
            venta.setDescuento(0.0);
        }

        venta.setFecha_venta(LocalDateTime.now());
        venta.setSubtotal(subtotal);
        venta.setIgv(subtotal * 0.18);
        venta.setTotal(subtotal + venta.getIgv());

        return ventaRepo.save(venta);
    }
}
