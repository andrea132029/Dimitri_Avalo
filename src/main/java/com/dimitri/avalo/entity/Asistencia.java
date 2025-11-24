package com.dimitri.avalo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Preparacion preparacion;
  
    @ManyToOne
    @JoinColumn(name = "familia_id", nullable = false)
    private Familia familia;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Boolean activa = true;

    // Getters y Setters
    public Long getId() { 
    	return id; }
    
    public void setId(Long id) { 
    	this.id = id; }

    public Familia getFamilia() {
    	return familia; }
    
    public Preparacion getPreparacion() {
		return preparacion;
	}

	public void setPreparacion(Preparacion preparacion) {
		this.preparacion = preparacion;
	}

	public void setFamilia(Familia familia) {
    	this.familia = familia; }

    public LocalDate getFecha() { 
    	return fecha; }
    
    public void setFecha(LocalDate fecha) { 
    	this.fecha = fecha; }

    public Boolean getActiva() {
    	return activa; }
    
    public void setActiva(Boolean activa) { 
    	this.activa = activa; }
}
