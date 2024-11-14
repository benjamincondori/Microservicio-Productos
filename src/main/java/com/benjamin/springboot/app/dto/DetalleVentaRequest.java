package com.benjamin.springboot.app.dto;

public record DetalleVentaRequest(
        String id,
        Integer cantidad,
        String productoId,
        String ventaId
) {
}
