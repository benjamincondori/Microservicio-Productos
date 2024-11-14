package com.benjamin.springboot.app.controllers;

import com.benjamin.springboot.app.dto.DetalleVentaRequest;
import com.benjamin.springboot.app.models.entity.DetalleVenta;
import com.benjamin.springboot.app.models.entity.Producto;
import com.benjamin.springboot.app.models.entity.Venta;
import com.benjamin.springboot.app.repositories.DetalleVentaRepository;
import com.benjamin.springboot.app.repositories.ProductoRepository;
import com.benjamin.springboot.app.repositories.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class DetalleVentaGraphQLController {

    private static final Logger logger = LoggerFactory.getLogger(DetalleVentaGraphQLController.class);

    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaGraphQLController(
            ProductoRepository productoRepository,
            VentaRepository ventaRepository,
            DetalleVentaRepository detalleVentaRepository
    ) {
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }


    @QueryMapping
    public List<DetalleVenta> listarDetalleVentas() {
        List<DetalleVenta> detalleVentas = detalleVentaRepository.findAll();
        return detalleVentas;
    }

    @QueryMapping
    public DetalleVenta listarDetalleVentaPorId(@Argument String id) {
        DetalleVenta detalleVenta = detalleVentaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("DetalleVenta con id %s no encontrado", id))
        );
        return detalleVenta;
    }

    @MutationMapping
    public DetalleVenta guardarDetalleVenta(@Argument DetalleVentaRequest detalleVentaRequest) {
        // Validar si el producto existe
        logger.info("DetalleVenta: " + detalleVentaRequest);
        String productoId = detalleVentaRequest.productoId();
        Producto producto = productoRepository.findById(productoId).orElseThrow(
                () -> new RuntimeException(String.format("Producto con id %s no encontrado", productoId))
        );

        // Validar si la venta existe
        String ventaId = detalleVentaRequest.ventaId();
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(
                () -> new RuntimeException(String.format("Venta con id %s no encontrada", ventaId))
        );

        if (detalleVentaRequest.cantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        // Inicializar la lista de detalles si es nula
        if (venta.getDetalles() == null) {
            venta.setDetalles(new ArrayList<>());
        }

        // Verificar si ya existe un DetalleVenta con el mismo producto en la venta
        Optional<DetalleVenta> detalleExistente = venta.getDetalles().stream()
                .filter(detalle -> detalle.getProducto().getId().equals(productoId))
                .findFirst();

        logger.info("detalleExistente: " + detalleExistente);

        if (detalleExistente.isPresent()) {
            // Si ya existe, incrementar la cantidad del DetalleVenta existente
            DetalleVenta detalleVentaEntity = detalleExistente.get();
            detalleVentaEntity.setCantidad(detalleVentaEntity.getCantidad() + detalleVentaRequest.cantidad());
            detalleVentaRepository.save(detalleVentaEntity);  // Guardar la actualización en la base de datos
            return detalleVentaEntity;
        } else {
            // Si no existe, crear un nuevo DetalleVenta
            DetalleVenta nuevoDetalleVenta = new DetalleVenta();
            nuevoDetalleVenta.setId(UUID.randomUUID().toString());
            nuevoDetalleVenta.setCantidad(detalleVentaRequest.cantidad());
            nuevoDetalleVenta.setProducto(producto);
            nuevoDetalleVenta.setVenta(venta);
            nuevoDetalleVenta = detalleVentaRepository.save(nuevoDetalleVenta);

            // Agregar el nuevo detalle a la lista existente de detalles en Venta
//            if (venta.getDetalles() == null) {
//                venta.setDetalles(new ArrayList<>());  // Inicializar la lista si está vacía
//            }
            venta.getDetalles().add(nuevoDetalleVenta);  // Agregar el nuevo detalle a la lista
            ventaRepository.save(venta);  // Guardar la venta con el detalle actualizado

            return nuevoDetalleVenta;
        }
    }

    @MutationMapping
    public DetalleVenta actualizarDetalleVenta(@Argument DetalleVentaRequest detalleVentaRequest, @Argument String id) {
        // Verificar si el detalleVenta existe
        DetalleVenta detalleVenta = detalleVentaRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Detalle de Venta con id %s no encontrado", id))
        );

        // Validar si el producto existe
        String productoId = detalleVentaRequest.productoId();
        Producto producto = productoRepository.findById(productoId).orElseThrow(
                () -> new RuntimeException(String.format("Producto con id %s no encontrado", productoId))
        );

        // Validar si la venta existe
        String ventaId = detalleVentaRequest.ventaId();
        Venta venta = ventaRepository.findById(ventaId).orElseThrow(
                () -> new RuntimeException(String.format("Venta con id %s no encontrada", ventaId))
        );

        if (detalleVentaRequest.cantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        detalleVenta.setCantidad(detalleVentaRequest.cantidad());
        detalleVenta.setProducto(producto);

        // Verificar si la venta ha cambiado
        if (!detalleVenta.getVenta().getId().equals(ventaId)) {
            // Si la venta ha cambiado, eliminar el detalle de la venta anterior
            Venta ventaAnterior = detalleVenta.getVenta();
            ventaAnterior.getDetalles().removeIf(d -> d.getId().equals(id));
            ventaRepository.save(ventaAnterior);

            // Actualizar la referencia de la venta en el detalle
            detalleVenta.setVenta(venta);

            // Agregar el detalle actualizado a la nueva venta
            if (venta.getDetalles() == null) {
                venta.setDetalles(new ArrayList<>());
            }
        } else {
            // Si la venta no cambió, solo actualizar el detalle en la venta actual
            venta.getDetalles().removeIf(d -> d.getId().equals(id));
        }
        venta.getDetalles().add(detalleVenta);
        ventaRepository.save(venta);

        // Guardar el detalle actualizado en el repositorio
        return detalleVentaRepository.save(detalleVenta);
    }

    @MutationMapping
    public String eliminarDetalleVenta(@Argument String id) {
        // Verificar si el detalleVenta existe
        if (detalleVentaRepository.existsById(id)) {
            detalleVentaRepository.deleteById(id);
            return String.format("Detalle de Venta con ID %s eliminado correctamente.", id);
        } else {
            throw new RuntimeException(String.format("Detalle de Venta con ID %s no encontrado", id));
        }
    }
}
