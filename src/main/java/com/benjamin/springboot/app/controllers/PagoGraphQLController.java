package com.benjamin.springboot.app.controllers;

import com.benjamin.springboot.app.dto.PagoRequest;
import com.benjamin.springboot.app.models.entity.Pago;
import com.benjamin.springboot.app.models.entity.Venta;
import com.benjamin.springboot.app.repositories.PagoRepository;
import com.benjamin.springboot.app.repositories.VentaRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PagoGraphQLController {

    private final PagoRepository pagoRepository;
    private final VentaRepository ventaRepository;

    public PagoGraphQLController(PagoRepository pagoRepository, VentaRepository ventaRepository) {
        this.pagoRepository = pagoRepository;
        this.ventaRepository = ventaRepository;
    }

    @QueryMapping
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    @QueryMapping
    public Pago listarPagoPorId(@Argument String id) {
        Pago pago = pagoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Pago con ID %s no encontrado", id))
        );
        return pago;
    }

    @QueryMapping
    public Optional<Pago> obtenerPagoPorVentaId(@Argument String ventaId) {
        return pagoRepository.findByVentaId(ventaId);
    }

    @MutationMapping
    public Pago guardarPago(@Argument PagoRequest pagoRequest) {
        // Validar que la venta exista
        Venta venta = ventaRepository.findById(pagoRequest.ventaId()).orElseThrow(
                () -> new RuntimeException(String.format("Venta con ID %s no encontrada", pagoRequest.ventaId()))
        );
        Pago pago = new Pago();
        pago.setId(UUID.randomUUID().toString());
        pago.setFecha(pagoRequest.fecha());
        pago.setMonto(pagoRequest.monto());
        pago.setMetodoPago(pagoRequest.metodoPago());
        pago.setDescripcion(pagoRequest.descripcion());
        pago.setVenta(venta);
        return pagoRepository.save(pago);
    }

    @MutationMapping
    public Pago actualizarPago(@Argument String id, @Argument PagoRequest pagoRequest) {
        // Validar que el pago exista
        Pago pago = pagoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Pago con ID %s no encontrado", id))
        );
        // Validar que la venta exista
        Venta venta = ventaRepository.findById(pagoRequest.ventaId()).orElseThrow(
                () -> new RuntimeException(String.format("Venta con ID %s no encontrada", pagoRequest.ventaId()))
        );
        pago.setFecha(pagoRequest.fecha());
        pago.setMonto(pagoRequest.monto());
        pago.setMetodoPago(pagoRequest.metodoPago());
        pago.setDescripcion(pagoRequest.descripcion());
        pago.setVenta(venta);
        return pagoRepository.save(pago);
    }

    @MutationMapping
    public String eliminarPago(@Argument String id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
            return String.format("Pago con ID %s eliminado correctamente.", id);
        } else {
            throw new RuntimeException(String.format("Pago con ID %s no encontrado", id));
        }
    }
}
