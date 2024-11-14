package com.benjamin.springboot.app.controllers;

import com.benjamin.springboot.app.models.entity.Marca;
import com.benjamin.springboot.app.repositories.MarcaRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class MarcaGraphQLController {

    private final MarcaRepository marcaRepository;

    public MarcaGraphQLController(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @QueryMapping
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    @QueryMapping
    public Marca listarMarcaPorId(@Argument String id) {
        return marcaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Marca con id %s no encontrada", id))
        );
    }

    @MutationMapping
    public Marca guardarMarca(@Argument String nombre) {
        Marca marca = new Marca();
        marca.setId(UUID.randomUUID().toString());
        marca.setNombre(nombre);

        return marcaRepository.save(marca);
    }

    @MutationMapping
    public Marca actualizarMarca(@Argument String nombre, @Argument String id) {
        Marca marca = marcaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Marca con id %s no encontrada", id))
        );
        marca.setNombre(nombre);

        return marcaRepository.save(marca);
    }

    @MutationMapping
    public String eliminarMarca(@Argument String id) {
        // Verificar si la marca existe
        if (marcaRepository.existsById(id)) {
            marcaRepository.deleteById(id);
            return String.format("Marca con ID %s eliminada correctamente.", id);
        } else {
            // Si no existe, lanzar una excepción
            throw new RuntimeException(String.format("Marca con ID %s no encontrada", id));
        }
    }

}
