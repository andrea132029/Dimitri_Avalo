package com.dimitri.avalo.dto;

public class IngredienteResumenDTO {

    private Long id;
    private String nombre;
    private Float cantidad; // cantidad necesaria en la receta

    public IngredienteResumenDTO() {}

    public IngredienteResumenDTO(Long id, String nombre, Float cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
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

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }
}
