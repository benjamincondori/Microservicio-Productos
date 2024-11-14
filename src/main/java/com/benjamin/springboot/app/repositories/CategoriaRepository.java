package com.benjamin.springboot.app.repositories;

import com.benjamin.springboot.app.models.entity.Categoria;
import com.benjamin.springboot.app.models.entity.Marca;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, String> {

//    Optional<Categoria> findById(String id);

}
