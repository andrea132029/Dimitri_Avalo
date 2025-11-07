package com.dimitri.avalo.dto;

import com.dimitri.avalo.entity.Condimento;
import com.dimitri.avalo.entity.Estacion;
import com.dimitri.avalo.entity.Ingrediente;
import com.dimitri.avalo.entity.Producto;
import com.dimitri.avalo.entity.TipoIngrediente;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


public class IngredienteDTO {
   @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
   @NotNull(message = "Las calorías son obligatorias")
   @DecimalMin(value = "0.1", message = "Las calorías deben ser mayores a 0")
    private Float  calorias;
   @NotNull(message = "Debe seleccionar una estación")
    private Estacion estacion;
   @PositiveOrZero(message = "El stock debe ser 0 o más")
    private Float  stockDisponible; 
   @NotNull(message = "Debe indicar si es Producto o Condimento")
	private TipoIngrediente tipo;       // PRODUCTO o CONDIMENTO
   @Positive (message = "El precio debe ser mayor a 0")
   private Float precioActual;

 
   private Long id;
   public IngredienteDTO() {
	}

   public IngredienteDTO(Ingrediente ingrediente) {
	    this.id = ingrediente.getId();
	    this.nombre = ingrediente.getNombre();
	    this.calorias = ingrediente.getCalorias();
	    this.estacion = ingrediente.getEstacion();

	    if (ingrediente instanceof Producto producto) {
	        this.tipo = TipoIngrediente.PRODUCTO;
	        this.stockDisponible = producto.getStockDisponible();
	        this.precioActual = producto.getPrecioActual();
	    } else if (ingrediente instanceof Condimento) {
	        this.tipo = TipoIngrediente.CONDIMENTO;
	    }
	}

   public Ingrediente toEntidad() {
	    if (tipo == TipoIngrediente.PRODUCTO) {
	        Producto p = new Producto();
	        p.setId(id);
	        p.setNombre(nombre);
	        p.setCalorias(calorias);
	        p.setEstacion(estacion);
	        p.setStockDisponible(stockDisponible);
	        p.setPrecioActual(precioActual);
	        p.setActivo(true);
	        return p;
	    } else {
	        Condimento c = new Condimento();
	        c.setId(id);
	        c.setNombre(nombre);
	        c.setCalorias(calorias);
	        c.setEstacion(estacion);
	        c.setActivo(true);
	        return c;
	    }
	}

   public Long getId() {
       return id;
   }

   public void setId(Long id) {
       this.id = id;
   }

    public Float getPrecioActual() {
	return precioActual;
}
public void setPrecioActual(Float precioActual) {
	this.precioActual = precioActual;
}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Float getCalorias() {
		return calorias;
	}
	public void setCalorias(Float calorias) {
		this.calorias = calorias;
	}
	public Estacion getEstacion() {
		return estacion;
	}
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}
	public Float getStockDisponible() {
		return stockDisponible;
	}
	public void setStockDisponible(Float stockDisponible) {
		this.stockDisponible = stockDisponible;
	}
	public TipoIngrediente getTipo() {
		return tipo;
	}
	public void setTipo(TipoIngrediente tipo) {
		this.tipo = tipo;
	}

   
}


