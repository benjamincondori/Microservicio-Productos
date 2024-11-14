package com.benjamin.springboot.app.repositories;

import com.benjamin.springboot.app.models.entity.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PagoRepository extends MongoRepository<Pago, String> {

    Optional<Pago> findByVentaId(String ventaId);
}
