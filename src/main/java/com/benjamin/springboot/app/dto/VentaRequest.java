package com.benjamin.springboot.app.dto;

public record VentaRequest(
        String fecha,
        Double monto,
        String clienteId,
        String vendedorId,
        String cajeroId
) {
}
