package com.dimitri.avalo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PreparacionRequestDTO {

    @NotNull(message = "Debe indicar la receta")
    private Long recetaId;

    @Min(value = 1, message = "Las raciones deben ser >= 1")
    private int raciones;

    public Long getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    public int getRaciones() {
        return raciones;
    }

    public void setRaciones(int raciones) {
        this.raciones = raciones;
    }
}

