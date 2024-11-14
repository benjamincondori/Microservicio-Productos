package com.benjamin.springboot.app.repositories;

import com.benjamin.springboot.app.models.entity.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends MongoRepository<Producto, String> {
}
