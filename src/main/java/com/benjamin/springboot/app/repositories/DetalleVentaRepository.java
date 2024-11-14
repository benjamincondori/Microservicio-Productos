package com.benjamin.springboot.app.repositories;

import com.benjamin.springboot.app.models.entity.DetalleVenta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetalleVentaRepository extends MongoRepository<DetalleVenta, String> {


}
