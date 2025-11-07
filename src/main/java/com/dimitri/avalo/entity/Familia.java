package com.dimitri.avalo.entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.dimitri.avalo.dto.IntegranteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity 
@Table(name = "familia")

public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private String nombre;  
    private LocalDate fechaAlta = LocalDate.now(); // Fecha de alta con valor por defecto 
    private LocalDate fechaUltimaAsistencia; 

    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Integrante> integrantes = new ArrayList<>();
 
    @OneToMany(mappedBy = "familia")
    private List<Asistido> asistidos;
    private String direccion;
     private boolean activa = true; 
    
   
    public Familia() {
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

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(@Min(value = 1, message = "Debe haber al menos 1 integrante") List<Integrante> list) {//asigamos una lista de integrantes ala familia
        this.integrantes = list;
     // Relación bidireccional
        for (Integrante i : list) {
            i.setFamilia(this);
        }
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
    // Método para agregar integrantes , También asegura que el integrante sepa a que familia pertenece.
    public void agregarIntegrante(Integrante integrante) {
        integrantes.add(integrante);
        integrante.setFamilia(this);
        
    }

	public LocalDate getFechaUltimaAsistencia() {
		return fechaUltimaAsistencia;
	}

	public void setFechaUltimaAsistencia(LocalDate fechaUltimaAsistencia) {
		this.fechaUltimaAsistencia = fechaUltimaAsistencia;
	}
	
	public long contarIntegrantesActivos() {//Devuelve la cantidad de integrantes activos.
	    return integrantes.stream()
	                     .filter(Integrante::isActivo)
	                     .count();
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	

}
