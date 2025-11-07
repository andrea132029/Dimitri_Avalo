package com.dimitri.avalo.dto;

import java.util.List;

import com.dimitri.avalo.dto.ItemRecetaDTO;

public class RecetaRequestDTO { //Datos para crear/editar

    private String nombre;
    private String descripcion;
    private List<ItemRecetaDTO> items;

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

    public List<ItemRecetaDTO> getItems() {
        return items;
    }
    public void setItems(List<ItemRecetaDTO> items) {
        this.items = items;
    }
}
