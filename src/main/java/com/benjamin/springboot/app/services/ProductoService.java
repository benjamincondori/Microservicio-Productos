package com.benjamin.springboot.app.services;

import com.benjamin.springboot.app.models.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> obtenerTodosLosProductos();
    Optional<Producto> obtenerProductoPorId(String id);
    Producto guardarProducto(Producto producto);
    Producto actualizarProducto(String id, Producto producto);
    void eliminarProducto(String id);


}
