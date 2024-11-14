package com.benjamin.springboot.app.repositories;

import com.benjamin.springboot.app.models.entity.Marca;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends MongoRepository<Marca, String> {

}
