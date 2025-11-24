package com.dimitri.avalo.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dimitri.avalo.entity.Asistencia;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findAllByFechaBetweenAndActivaTrue(LocalDate desde, LocalDate hasta);
    boolean existsByFamiliaIdAndFechaAndActivaTrue(Long familiaId, LocalDate fecha);
}
