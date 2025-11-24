package com.dimitri.avalo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "receta_simple")
public class RecetaSimple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Float pesoRacion;

    @Column(nullable = false)
    private Integer caloriasRacion;

    @Column(nullable = false)
    private Boolean activa = true;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPesoRacion() {
        return pesoRacion;
    }

    public void setPesoRacion(Float pesoRacion) {
        this.pesoRacion = pesoRacion;
    }

    public Integer getCaloriasRacion() {
        return caloriasRacion;
    }

    public void setCaloriasRacion(Integer caloriasRacion) {
        this.caloriasRacion = caloriasRacion;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
