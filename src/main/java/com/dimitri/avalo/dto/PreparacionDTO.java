package com.dimitri.avalo.dto;

import java.time.LocalDate;

public class PreparacionDTO {

    private Long id;
    private Long recetaId;
    private Integer racionesGeneradas;
    private LocalDate fechaPreparacion = LocalDate.now();
    private String recetaNombre;
    
    public PreparacionDTO() {
    	
    }
    

    public Long getId() {
    	return id; }

    public void setId(Long id) { 
    	this.id = id; }

    public Long getRecetaId() { 
    	return recetaId; }

    public void setRecetaId(Long recetaId) {
    	this.recetaId = recetaId; }

    public Integer getRacionesGeneradas() { 
    	return racionesGeneradas; }

    public void setRacionesGeneradas(Integer racionesGeneradas) { 
        this.racionesGeneradas = racionesGeneradas; 
    }

    public LocalDate getFechaPreparacion() { 
    	return fechaPreparacion; }

    public void setFechaPreparacion(LocalDate fechaPreparacion) { 
        this.fechaPreparacion = fechaPreparacion; 
    }
    
    public String getRecetaNombre() {
        return recetaNombre;
    }

    public void setRecetaNombre(String recetaNombre) {
        this.recetaNombre = recetaNombre;
    }
}
