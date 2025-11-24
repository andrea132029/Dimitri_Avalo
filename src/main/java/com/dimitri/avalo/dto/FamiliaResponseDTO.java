package com.dimitri.avalo.dto;

import org.springframework.hateoas.RepresentationModel;

import com.dimitri.avalo.entity.Familia;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta del servicio para una familia registrada")
public class FamiliaResponseDTO extends RepresentationModel<FamiliaResponseDTO> {

    @Schema(description = "ID único de la familia", example = "1")
    private Long id;

    @Schema(description = "Nombre de la familia", example = "Gómez")
    private String nombre;

    @Schema(description = "Dirección del hogar", example = "Calle 123, Barrio Centro")
    private String direccion;

    @Schema(description = "Cantidad total de integrantes activos", example = "4")
    private int cantidadIntegrantes;

    @Schema(description = "Estado de la familia (activa o no)", example = "true")
    private boolean activa;

    
    public FamiliaResponseDTO(Familia familia) {
		
	}

	//Links HATEOAS
    public void addSelfLink() {
        this.add(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                .linkTo(org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
                        .methodOn(com.dimitri.avalo.controller.FamiliaController.class)
                        .getFamiliaById(this.id))
                .withSelfRel());
    }

    // Getters y Setters
    public Long getId() {
    	return id; }
    
    public void setId(Long id) { 
    	this.id = id; }
    
    public String getNombre() {
    	return nombre; }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre; }
    
    public String getDireccion() { 
    	return direccion; }
    
    public void setDireccion(String direccion) { 
    	this.direccion = direccion; }
    
    public int getCantidadIntegrantes() {
    	return cantidadIntegrantes; }
    
    public void setCantidadIntegrantes(int cantidadIntegrantes) { 
    	this.cantidadIntegrantes = cantidadIntegrantes; }
    
    public boolean isActiva() {
    	return activa; }
    
    public void setActiva(boolean activa) {
    	this.activa = activa; }
    
    
}
