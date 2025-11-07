package com.dimitri.avalo.service;

import java.time.LocalDate;
import java.util.List;

import com.dimitri.avalo.entity.Entrega;

public interface EntregaService {
    List<Entrega> listarPorFecha(LocalDate fecha);
    List<Entrega> listarPorFechaYFamilia(LocalDate fecha, Long familiaId);
    List<Entrega> listarPorFechaYNombreFamilia(LocalDate fecha, String nombreFamilia);
    
    Entrega registrar(Entrega e);
    void eliminar(Long id);
    Entrega buscarPorId(Long id);
}

