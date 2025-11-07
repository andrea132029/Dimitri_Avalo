package com.dimitri.avalo.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dimitri.avalo.entity.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {

  // Para validar unicidad: no dos entregas el mismo día a la misma familia
  boolean existsByFamiliaIdAndFechaEntregaAndActivoTrue(Long familiaId, LocalDate fecha);

  // Para el listado por fecha
  List<Entrega> findAllByFechaEntregaAndActivoTrue(LocalDate fecha);
  List<Entrega> findAllByFechaEntregaAndFamiliaIdAndActivoTrue(LocalDate fecha, Long familiaId);
  List<Entrega> findAllByFechaEntregaAndFamiliaNombreContainingIgnoreCaseAndActivoTrue(LocalDate fecha, String nombreFamilia);
  
}
