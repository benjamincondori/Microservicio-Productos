package com.benjamin.springboot.app.repositories;

import com.benjamin.springboot.app.models.entity.Factura;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FacturaRepository extends MongoRepository<Factura, String> {
}
