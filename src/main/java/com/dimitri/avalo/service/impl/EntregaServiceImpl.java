package com.dimitri.avalo.service.impl;


import java.time.LocalDate;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimitri.avalo.entity.Entrega;
import com.dimitri.avalo.entity.Preparacion;
import com.dimitri.avalo.repository.EntregaRepository;
import com.dimitri.avalo.service.EntregaService;
import com.dimitri.avalo.service.FamiliaService;
import com.dimitri.avalo.service.PreparacionService;

import java.util.List;

@Service
public class EntregaServiceImpl implements EntregaService {

  @Autowired
  private EntregaRepository entregaRepo;

  @Autowired
  private FamiliaService familiaService;
  
  @Autowired
  private PreparacionService prepService;

  @Override
  public Entrega registrar(Entrega e) {
    // 1) Unicidad por día y familia
    if(entregaRepo.existsByFamiliaIdAndFechaEntregaAndActivoTrue(
         e.getFamilia().getId(), e.getFechaEntrega())) {
      throw new IllegalArgumentException("Ya existe una entrega hoy para esa familia");
    }
    // 2) No pasar integrantes
    int integrantes = familiaService.contarIntegrantesActivos(e.getFamilia().getId());

    if(e.getCantidad() > integrantes) {
      throw new IllegalArgumentException("Cantidad excede integrantes de la familia");
    }
    // 3) Descontar stock de raciones
    Preparacion prep = prepService.buscarPorId(e.getPreparacion().getId());
    prep.setCantidadRaciones(prep.getCantidadRaciones() - e.getCantidad());
    // <-- antes llamabas a registrarPreparacion y eso re-disparaba la excepción de duplicado
    prepService.guardarPreparacion(prep);
    return entregaRepo.save(e);
  }

  @Override
  public void eliminar(Long id) {
    var e = entregaRepo.findById(id)
       .orElseThrow(() -> new NoSuchElementException("Entrega no encontrada"));
    e.setActivo(false);
    entregaRepo.save(e);
    // Devolver stock
    var prep = e.getPreparacion();
    prep.setCantidadRaciones(prep.getCantidadRaciones() + e.getCantidad());
    prepService.registrarPreparacion(prep);
  }

  @Override
  public List<Entrega> listarPorFecha(LocalDate fecha) {
    return entregaRepo.findAllByFechaEntregaAndActivoTrue(fecha);
  }

  @Override
  public Entrega buscarPorId(Long id) {
    return entregaRepo.findById(id)
       .orElseThrow(() -> new NoSuchElementException("Entrega no encontrada"));
  }
  @Override
  public List<Entrega> listarPorFechaYFamilia(LocalDate fecha, Long familiaId) {
    return entregaRepo.findAllByFechaEntregaAndFamiliaIdAndActivoTrue(fecha, familiaId);
  }

  @Override
  public List<Entrega> listarPorFechaYNombreFamilia(LocalDate fecha, String nombreFamilia) {
    return entregaRepo.findAllByFechaEntregaAndFamiliaNombreContainingIgnoreCaseAndActivoTrue(fecha, nombreFamilia);
  }
}

