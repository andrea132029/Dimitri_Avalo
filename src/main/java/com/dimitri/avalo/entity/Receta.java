package com.dimitri.avalo.entity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"items"})
@Entity 
@Table(name = "receta")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(unique = true, nullable = false)
    private String nombre;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Float pesoRacion;

    @Column(nullable = false)
    private Integer caloriasRacion; 
    
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
	public void setActiva(Boolean activa) {
		this.activa = activa;
	}
	@Size(min = 1, message = "La receta debe tener al menos un ingrediente")
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true) 
    @JsonIgnore
    @Valid
    private List<ItemReceta> items = new ArrayList<>();

    @Column(nullable = false)
    private Boolean activa = true;


    public Receta() {
    	
    }
    public Receta(String nombre, String descripcion) {
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    	}
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
    public String getDescripcion() { 
    	return descripcion; 
    	}
    public void setDescripcion(String descripcion) { 
    	this.descripcion = descripcion; 
    	}
    public List<ItemReceta> getItems() {
    	return items; 
    	}
    public void setItems(List<ItemReceta> items) {
    	this.items = items;
    	}
    public Boolean getActiva() {
    	return activa; 
    	}
    public void setActiva(boolean activa) { 
    	this.activa = activa; 
    	}
    @Transient
    public Float getCaloriasTotales() {
        if (items == null) return 0f;

        return items.stream()
            .filter(i -> i.getActiva() == null || i.getActiva())
            .map(i -> i.getIngrediente().getCalorias() * i.getCantidad())
            .reduce(0f, Float::sum);
    }



}
