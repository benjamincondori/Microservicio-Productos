package com.benjamin.springboot.app.services;

import com.benjamin.springboot.app.models.entity.Producto;
import com.benjamin.springboot.app.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }

    @Override
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto actualizarProducto(String id, Producto productoActualizado) {
        if (productoRepository.existsById(id)) {
            productoActualizado.setId(id);
            return productoRepository.save(productoActualizado);
        }
        return null;
    }

    @Override
    @Transactional
    public void eliminarProducto(String id) {
        productoRepository.deleteById(id);
    }
}
