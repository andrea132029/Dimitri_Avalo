package com.dimitri.avalo.dto;

public class RecetaSimpleRequestDTO {

    public String nombre;
    public Float pesoRacion;
    public Integer caloriasRacion;
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
    
    
}
