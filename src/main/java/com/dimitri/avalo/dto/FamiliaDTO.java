package com.dimitri.avalo.dto;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.dimitri.avalo.entity.Familia;
import com.dimitri.avalo.entity.Integrante;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

@Schema(description = "Objeto utilizado para registrar una familia en el sistema")
public class FamiliaDTO {

	    private Long id;
	    @Schema(description = "Nombre de la familia", example = "Gómez")
	    @NotBlank(message = "El nombre no puede estar vacío")
	    private String nombre;

	    @PastOrPresent(message = "La fecha no puede ser futura")
	    private LocalDate fechaAlta = LocalDate.now();
	    @Valid
	    private int cantidadIntegrantes;
	    @Schema(description = "Dirección del hogar", example = "Calle 123, Barrio Centro")
	    private String direccion;
		private boolean activa = true;
		private Object integrantes;
	    
	    public FamiliaDTO() {
	    	
	    }

	    public FamiliaDTO(Long id, String nombre, int cantidadIntegrantes, LocalDate fechaAlta, String direccion) {
	        this.id = id;
	        this.nombre = nombre;
	        this.cantidadIntegrantes = cantidadIntegrantes;
	        this.fechaAlta = fechaAlta;
	        this.direccion = direccion;
	    }

	    // Convierte el form a entidad para el service
	    public Familia toEntidad() {
	        Familia familia = new Familia();
	        familia.setId(this.id);
	        familia.setNombre(this.nombre);
	        familia.setFechaAlta(this.fechaAlta);
	        familia.setActiva(this.activa);
	        
	        List<Integrante> lista = ((Collection<Integrante>) this.integrantes).stream().map(form -> {
	            Integrante i = new Integrante();
	            
	            i.setDni(form.getDni());
	            i.setNombre(form.getNombre());
	            i.setApellido(form.getApellido());
	            i.setFechaNacimiento(form.getFechaNacimiento());
	            i.setDomicilio(form.getDomicilio());
	            i.setEdad(form.getEdad());
	            i.setParentesco(form.getParentesco());
	            i.setOcupacion(form.getOcupacion());
	            i.setActivo(true);
	            i.setFamilia(familia); // establece la relación
	            return i;
	        }).collect(Collectors.toList());

	        familia.setIntegrantes(lista);
	        return familia;
	    }
	    public int getCantidadIntegrantes() {
			return cantidadIntegrantes;
		}

		public void setCantidadIntegrantes(int cantidadIntegrantes) {
			this.cantidadIntegrantes = cantidadIntegrantes;
		}

		public String getDireccion() {
			return direccion;
		}

		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}

		public Long getId() {
	    	return id; }
	    
	    public void setId(Long id) { 
	    	this.id = id; }

	    public String getNombre() {
	    	return nombre; }
	    
	    public void setNombre(String nombre) { 
	    	this.nombre = nombre; }

	    public LocalDate getFechaAlta() { 
	    	return fechaAlta; }
	    
	    public void setFechaAlta(LocalDate fechaAlta) {
	    	this.fechaAlta = fechaAlta; }

	    public boolean isActiva() {
	    	return activa; }
	    
	    public void setActiva(boolean activa) { 
	    	this.activa = activa; }

	    

	}