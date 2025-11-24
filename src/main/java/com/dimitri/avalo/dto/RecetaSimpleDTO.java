package com.dimitri.avalo.dto;

public class RecetaSimpleDTO {

    public Long id;
    public String nombre;
    public Float pesoRacion;
    public Integer caloriasRacion;

    public String linkPreparaciones;

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

	public String getLinkPreparaciones() {
		return linkPreparaciones;
	}

	public void setLinkPreparaciones(String linkPreparaciones) {
		this.linkPreparaciones = linkPreparaciones;
	} 
    
}
