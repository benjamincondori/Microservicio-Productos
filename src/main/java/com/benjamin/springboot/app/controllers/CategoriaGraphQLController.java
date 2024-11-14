package com.benjamin.springboot.app.controllers;

import com.benjamin.springboot.app.models.entity.Categoria;
import com.benjamin.springboot.app.repositories.CategoriaRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CategoriaGraphQLController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaGraphQLController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @QueryMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @QueryMapping
    public Categoria listarCategoriaPorId(@Argument String id) {
        return categoriaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Categoría con id %s no encontrada", id))
        );
    }

    @MutationMapping
    public Categoria guardarCategoria(@Argument String nombre) {
        Categoria categoria = new Categoria();
        categoria.setId(UUID.randomUUID().toString());
        categoria.setNombre(nombre);

        return categoriaRepository.save(categoria);
    }

    @MutationMapping
    public Categoria actualizarCategoria(@Argument String nombre, @Argument String id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Categoría con id %s no encontrada", id))
        );
        categoria.setNombre(nombre);

        return categoriaRepository.save(categoria);
    }

    @MutationMapping
    public String eliminarCategoria(@Argument String id) {
        // Verificar si la categoría existe
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return String.format("Categoría con ID %s eliminada correctamente.", id);
        } else {
            // Si no existe, lanzar una excepción
            throw new RuntimeException(String.format("Categoría con id %s no encontrada", id));
        }
    }

}
