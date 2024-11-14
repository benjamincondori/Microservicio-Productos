package com.benjamin.springboot.app.dto;

import com.benjamin.springboot.app.models.entity.Categoria;
import com.benjamin.springboot.app.models.entity.Marca;

public record ProductoRequest(
        String id,
        String nombre,
        String descripcion,
        double precio,
        int stock,
        String estado,
        String foto_url,
        Integer longitud,
        Integer ancho,
        Integer altura,
        Double peso,
        String categoriaId,
        String marcaId
) {
}
