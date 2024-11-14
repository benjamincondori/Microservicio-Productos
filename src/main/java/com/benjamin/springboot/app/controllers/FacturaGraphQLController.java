package com.benjamin.springboot.app.controllers;

import com.benjamin.springboot.app.dto.FacturaRequest;
import com.benjamin.springboot.app.models.entity.Factura;
import com.benjamin.springboot.app.models.entity.Pago;
import com.benjamin.springboot.app.repositories.FacturaRepository;
import com.benjamin.springboot.app.repositories.PagoRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class FacturaGraphQLController {

    private final PagoRepository pagoRepository;
    private final FacturaRepository facturaRepository;

    public FacturaGraphQLController(PagoRepository pagoRepository, FacturaRepository facturaRepository) {
        this.pagoRepository = pagoRepository;
        this.facturaRepository = facturaRepository;
    }

    @QueryMapping
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    @QueryMapping
    public Factura listarFacturaPorId(@Argument String id) {
        Factura factura = facturaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Factura con ID %s no encontrada", id))
        );
        return factura;
    }

    @MutationMapping
    public Factura guardarFactura(@Argument FacturaRequest facturaRequest) {
        // Validar que el pago exista
        Pago pago = pagoRepository.findById(facturaRequest.pagoId()).orElseThrow(
                () -> new RuntimeException(String.format("Pago con ID %s no encontrado", facturaRequest.pagoId()))
        );

        Factura factura = new Factura();
        factura.setId(UUID.randomUUID().toString());
        factura.setFechaEmision(facturaRequest.fechaEmision());
        factura.setMontoTotal(facturaRequest.montoTotal());
        factura.setImporte(facturaRequest.importe());
        factura.setSaldo(facturaRequest.saldo());
        factura.setPago(pago);
        return facturaRepository.save(factura);
    }

    @MutationMapping
    public Factura actualizarFactura(@Argument String id, @Argument FacturaRequest facturaRequest) {
        // Validar que la factura exista
        Factura factura = facturaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Factura con ID %s no encontrada", id))
        );

        // Validar que el pago exista
        Pago pago = pagoRepository.findById(facturaRequest.pagoId()).orElseThrow(
                () -> new RuntimeException(String.format("Pago con ID %s no encontrado", facturaRequest.pagoId()))
        );

        factura.setFechaEmision(facturaRequest.fechaEmision());
        factura.setMontoTotal(facturaRequest.montoTotal());
        factura.setImporte(facturaRequest.importe());
        factura.setSaldo(facturaRequest.saldo());
        factura.setPago(pago);
        return facturaRepository.save(factura);
    }

    @MutationMapping
    public String eliminarFactura(@Argument String id) {
        if (facturaRepository.existsById(id)) {
            facturaRepository.deleteById(id);
            return String.format("Factura con ID %s eliminada correctamente.", id);
        } else {
            throw new RuntimeException(String.format("Factura con ID %s no encontrada", id));
        }
    }

}
