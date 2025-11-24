package com.dimitri.avalo.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;

@Entity
public class Integrante extends Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Min(value = 0, message = "La edad debe ser 0 o mayor")
	private Integer edad;
    private String parentesco;

    @Enumerated(EnumType.STRING)
    private Ocupacion ocupacion;

    private boolean activo = true;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "familia_id")
    private Familia familia;

   

    public Integrante() {
		super();
		
	}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Ocupacion getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(Ocupacion ocupacion) {
        this.ocupacion = ocupacion;
    }


    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

	public boolean isActivo() {
		return activo;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	

}
