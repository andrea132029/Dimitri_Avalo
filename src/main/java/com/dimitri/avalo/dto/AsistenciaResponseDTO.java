package com.dimitri.avalo.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.dimitri.avalo.controller.AsistenciaController;
import com.dimitri.avalo.controller.FamiliaController;
import com.dimitri.avalo.controller.RecetaController;
import com.dimitri.avalo.entity.Asistencia;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de salida para mostrar los datos de una asistencia registrada.
 */
@Schema(description = "Datos devueltos al consultar una asistencia")
public class AsistenciaResponseDTO extends RepresentationModel<AsistenciaResponseDTO> {

    @Schema(description = "Identificador único de la asistencia", example = "1")
    private Long id;

    @Schema(description = "Nombre de la familia asociada", example = "Familia Gómez")
    private String nombreFamilia;

    @Schema(description = "Fecha en la que se registró la asistencia", example = "2025-11-12")
    private LocalDate fecha;

    @Schema(description = "Indica si la asistencia está activa o anulada", example = "true")
    private Boolean activa;

    public AsistenciaResponseDTO(Asistencia asistencia) {
        this.id = asistencia.getId();
        this.nombreFamilia = asistencia.getFamilia() != null ? asistencia.getFamilia().getNombre() : null;
        this.fecha = asistencia.getFecha();
        this.activa = asistencia.getActiva();
    }

    // ============================
    //       HATEOAS LINKS
    // ============================
    public void addLinks(Asistencia asistencia) {

        // --- SELF LINK ---
        this.add(linkTo(methodOn(AsistenciaController.class)
                .listar(null, null))
                .slash(asistencia.getId())
                .withSelfRel()
        );

        // --- Link a familia ---
        if (asistencia.getFamilia() != null) {
            this.add(linkTo(methodOn(FamiliaController.class)
                    .getFamiliaById(asistencia.getFamilia().getId()))
                    .withRel("familia"));
        }

        // --- Link a receta (via preparación) ---
        if (asistencia.getPreparacion() != null &&
                asistencia.getPreparacion().getReceta() != null) {

            Long idReceta = asistencia.getPreparacion().getReceta().getId();

            this.add(linkTo(methodOn(RecetaController.class)
                    .getRecetaById(idReceta))
                    .withRel("receta"));
        }
    }

    public Long getId() {  
    	return id; }
    
    public String getNombreFamilia() {
    	return nombreFamilia; }
    
    public LocalDate getFecha() {
    	return fecha; }
    
    public Boolean getActiva() { 
    	return activa; }

    
 
}
