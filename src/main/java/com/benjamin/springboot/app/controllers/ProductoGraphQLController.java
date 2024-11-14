package com.benjamin.springboot.app.controllers;

import com.benjamin.springboot.app.dto.ProductoRequest;
import com.benjamin.springboot.app.dto.ProductoResponse;
import com.benjamin.springboot.app.models.entity.Categoria;
import com.benjamin.springboot.app.models.entity.Marca;
import com.benjamin.springboot.app.models.entity.Producto;
import com.benjamin.springboot.app.repositories.CategoriaRepository;
import com.benjamin.springboot.app.repositories.MarcaRepository;
import com.benjamin.springboot.app.repositories.ProductoRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ProductoGraphQLController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;

    public ProductoGraphQLController(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            MarcaRepository marcaRepository
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
    }

    @QueryMapping
    public List<ProductoResponse> listarProductos() {
        List<Producto> productos = productoRepository.findAll();

        // Convertir productos a ProductoResponse para incluir el objeto completo de categoría y marca
        return productos.stream().map(producto -> {
            Categoria categoria = categoriaRepository.findById(producto.getCategoriaId()).orElse(null);
            Marca marca = marcaRepository.findById(producto.getMarcaId()).orElse(null);
            return new ProductoResponse(producto, categoria, marca);
        }).collect(Collectors.toList());
    }

    @QueryMapping
    public ProductoResponse listarProductoPorId(@Argument String id) {
        Producto producto = productoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Producto con id %s no encontrado", id))
        );
        Categoria categoria = categoriaRepository.findById(producto.getCategoriaId()).orElse(null);
        Marca marca = marcaRepository.findById(producto.getMarcaId()).orElse(null);
        return new ProductoResponse(producto, categoria, marca);
    }

    @MutationMapping
    public ProductoResponse guardarProducto(@Argument ProductoRequest productoRequest) {

        // Validar existencia de la categoría
        String categoriaId = productoRequest.categoriaId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));

        // Validar existencia de la marca
        String marcaId = productoRequest.marcaId();
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + marcaId));

        // Crear el producto con los datos de productoRequest
        Producto producto = new Producto();
        producto.setId(UUID.randomUUID().toString());
        producto.setNombre(productoRequest.nombre());
        producto.setDescripcion(productoRequest.descripcion());
        producto.setPrecio(productoRequest.precio());
        producto.setStock(productoRequest.stock());
        producto.setEstado(productoRequest.estado());
        producto.setFoto_url(productoRequest.foto_url());
        producto.setLongitud(productoRequest.longitud());
        producto.setAncho(productoRequest.ancho());
        producto.setAltura(productoRequest.altura());
        producto.setPeso(productoRequest.peso());
        producto.setCategoriaId(categoriaId); // Solo guarda el ID de la categoría
        producto.setMarcaId(marcaId);         // Solo guarda el ID de la marca

        // Guardar el producto en la base de datos
        producto = productoRepository.save(producto);

        // Crear y devolver el ProductoResponse con el objeto completo de categoría y marca
        return new ProductoResponse(producto, categoria, marca);
    }

    @MutationMapping
    public ProductoResponse actualizarProducto(@Argument ProductoRequest productoRequest, @Argument String id) {
        // Verificar si el producto existe
        Producto producto = productoRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Producto con id %s no encontrado", id))
        );

        // Validar existencia de la categoría
        String categoriaId = productoRequest.categoriaId();
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));

        // Validar existencia de la marca
        String marcaId = productoRequest.marcaId();
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + marcaId));

        producto.setNombre(productoRequest.nombre());
        producto.setDescripcion(productoRequest.descripcion());
        producto.setPrecio(productoRequest.precio());
        producto.setStock(productoRequest.stock());
        producto.setEstado(productoRequest.estado());
        producto.setFoto_url(productoRequest.foto_url());
        producto.setLongitud(productoRequest.longitud());
        producto.setAncho(productoRequest.ancho());
        producto.setAltura(productoRequest.altura());
        producto.setPeso(productoRequest.peso());
        producto.setCategoriaId(categoriaId); // Solo guarda el ID de la categoría
        producto.setMarcaId(marcaId);         // Solo guarda el ID de la marca

        // Guardar el producto en la base de datos
        producto = productoRepository.save(producto);

        // Crear y devolver el ProductoResponse con el objeto completo de categoría y marca
        return new ProductoResponse(producto, categoria, marca);
    }

    @MutationMapping
    public String eliminarProducto(@Argument String id) {
        // Verificar si el producto existe
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return String.format("Producto con ID %s eliminado correctamente.", id);
        } else {
            // Lanzar una excepción para que GraphQL devuelva un error
            throw new RuntimeException(String.format("Producto con ID %s no encontrado", id));
        }
    }

}
