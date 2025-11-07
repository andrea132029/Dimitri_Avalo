package com.dimitri.avalo.dto;

import java.util.ArrayList;
import java.util.List;

public class RecetaDTO {

    private Long id;
    private String nombre;
    private Float caloriasTotales;

    private List<IngredienteResumenDTO> ingredientes = new ArrayList<>();

    public RecetaDTO() {}

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

