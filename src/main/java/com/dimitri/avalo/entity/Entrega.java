package com.dimitri.avalo.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "familia_id", nullable = false)
    private Familia familia;

    @ManyToOne
    @JoinColumn(name = "preparacion_id", nullable = false)
    private Preparacion preparacion;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(nullable = false)
    private Boolean activo = true;

    // ——— Getters & Setters ———

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Familia getFamilia() { return familia; }
    public void setFamilia(Familia familia) { this.familia = familia; }

    public Preparacion getPreparacion() { return preparacion; }
    public void setPreparacion(Preparacion preparacion) { this.preparacion = preparacion; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}