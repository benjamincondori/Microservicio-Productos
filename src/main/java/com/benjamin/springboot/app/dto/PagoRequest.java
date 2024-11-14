package com.benjamin.springboot.app.dto;

public record PagoRequest(
        String fecha,
        Double monto,
        String metodoPago,
        String descripcion,
        String ventaId
) {
}
