package com.benjamin.springboot.app.repositories;

import com.benjamin.springboot.app.models.entity.Venta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VentaRepository extends MongoRepository<Venta, String> {
}
