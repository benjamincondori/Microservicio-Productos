package com.benjamin.springboot.app.dto;

public record FacturaRequest(
        String fechaEmision,
        Double montoTotal,
        Double importe,
        Double saldo,
        String pagoId
) {
}
