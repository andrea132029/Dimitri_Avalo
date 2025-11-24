package com.dimitri.avalo.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para registrar o modificar una asistencia.
 * Representa los datos mínimos que necesita el backend
 * para crear o actualizar una asistencia.
 */
    @Schema(description = "Datos necesarios para registrar o actualizar una asistencia")
    public class AsistenciaDTO {

    @Schema(description = "ID de la familia asociada a la asistencia", example = "1", required = true)
    @NotNull(message = "La familia es obligatoria")
    private Long idFamilia;

    @Schema(description = "ID de la preparación/ración entregada", example = "5", required = true)
    @NotNull(message = "La preparación es obligatoria")
    private Long idPreparacion;

    @Schema(description = "Fecha de entrega de la asistencia", example = "2025-11-13", required = true)
    @NotNull(message = "La fecha de entrega es obligatoria")
    private LocalDate fecha;

    public Long getIdPreparacion() {
		return idPreparacion;
	}

	public void setIdPreparacion(Long idPreparacion) {
		this.idPreparacion = idPreparacion;
	}

	public Long getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(Long idFamilia) {
        this.idFamilia = idFamilia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
