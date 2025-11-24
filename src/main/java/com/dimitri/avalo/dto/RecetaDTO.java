package com.dimitri.avalo.dto;

import java.util.ArrayList;
import java.util.List;

public class RecetaDTO {

    private Long id;
    private String nombre;
    private Float caloriasTotales;
    public Float pesoRacion;
    public Integer caloriasRacion;
    public String linkPreparaciones;
    
    public String getLinkPreparaciones() {
		return linkPreparaciones;
	}

	public void setLinkPreparaciones(String linkPreparaciones) {
		this.linkPreparaciones = linkPreparaciones;
	}

	private List<IngredienteResumenDTO> ingredientes = new ArrayList<>();

    public RecetaDTO() {
    	
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

    public Float getCaloriasTotales() {
        return caloriasTotales;
    }

    public void setCaloriasTotales(Float caloriasTotales) {
        this.caloriasTotales = caloriasTotales;
    }

    public List<IngredienteResumenDTO> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteResumenDTO> ingredientes) {
        this.ingredientes = ingredientes;
    }
}

