package com.benjamin.springboot.app.dto;

import com.benjamin.springboot.app.models.entity.Categoria;
import com.benjamin.springboot.app.models.entity.Marca;
import com.benjamin.springboot.app.models.entity.Producto;

public class ProductoResponse {
    private String id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String estado;
    private String foto_url;
    private Integer longitud;
    private Integer ancho;
    private Integer altura;
    private Double peso;
    private Categoria categoria;
    private Marca marca;

    // Constructor que toma un Producto y una Categoria
    public ProductoResponse(Producto producto, Categoria categoria, Marca marca) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.descripcion = producto.getDescripcion();
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        this.estado = producto.getEstado();
        this.foto_url = producto.getFoto_url();
        this.longitud = producto.getLongitud();
        this.ancho = producto.getAncho();
        this.altura = producto.getAltura();
        this.peso = producto.getPeso();
        this.categoria = categoria;
        this.marca = marca;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFotoUrl() {
        return foto_url;
    }

    public void setFotoUrl(String foto_url) {
        this.foto_url = foto_url;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public Integer getAncho() {
        return ancho;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
