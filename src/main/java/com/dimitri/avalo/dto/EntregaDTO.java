package com.dimitri.avalo.dto;

import java.time.LocalDate;

public class EntregaDTO {

    private Long id;
    private Long familiaId;
    private Integer racionesEntregadas;
    private LocalDate fechaEntrega = LocalDate.now();

    public EntregaDTO() {
    	
    }

    public Long getId() {
    	return id; }

    public void setId(Long id) { 
    	this.id = id; }

    public Long getFamiliaId() { 
    	return familiaId; }

    public void setFamiliaId(Long familiaId) { 
    	this.familiaId = familiaId; }

    public Integer getRacionesEntregadas() { 
    	return racionesEntregadas; }

    public void setRacionesEntregadas(Integer racionesEntregadas) { 
        this.racionesEntregadas = racionesEntregadas; 
    }

    public LocalDate getFechaEntrega() {
    	return fechaEntrega; }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
