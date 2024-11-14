package com.benjamin.springboot.app.controllers;

import com.benjamin.springboot.app.dto.VentaRequest;
import com.benjamin.springboot.app.models.entity.*;
import com.benjamin.springboot.app.repositories.VentaRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VentaGraphQLController {

    private final VentaRepository ventaRepository;

    public VentaGraphQLController(
            VentaRepository ventaRepository
    ) {
        this.ventaRepository = ventaRepository;
    }

    @QueryMapping
    public List<Venta> listarVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas;
    }

    @QueryMapping
    public Venta listarVentaPorId(@Argument String id) {
        Venta venta = ventaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Venta con id %s no encontrado", id))
        );
        return venta;
    }

    @MutationMapping
    public Venta guardarVenta(@Argument VentaRequest ventaRequest) {
        // Validar si el cliente existe

        // Validar si el vendedor existe

        // Validar si el cajero existe

        Venta venta = new Venta();
        venta.setId(UUID.randomUUID().toString());
        venta.setFecha(ventaRequest.fecha());
        venta.setMonto(ventaRequest.monto());
        venta.setClienteId(ventaRequest.clienteId());
        venta.setVendedorId(ventaRequest.vendedorId());
        venta.setCajeroId(ventaRequest.cajeroId());

        return ventaRepository.save(venta);
    }

    @MutationMapping
    public Venta actualizarVenta(@Argument VentaRequest ventaRequest,  @Argument String id) {
        // Verificar si la venta existe
        Venta venta = ventaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Venta con id %s no encontrado", id))
        );

        // Validar si el cliente existe

        // Validar si el vendedor existe

        // Validar si el cajero existe

        venta.setFecha(ventaRequest.fecha());
        venta.setMonto(ventaRequest.monto());
        venta.setClienteId(ventaRequest.clienteId());
        venta.setVendedorId(ventaRequest.vendedorId());
        venta.setCajeroId(ventaRequest.cajeroId());

        return ventaRepository.save(venta);
    }

    @MutationMapping
    public String eliminarVenta(@Argument String id) {
        // Verificar si la venta existe
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return String.format("Venta con ID %s eliminado correctamente.", id);
        } else {
            throw new RuntimeException(String.format("Venta con ID %s no encontrado", id));
        }
    }

}
