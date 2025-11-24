package com.dimitri.avalo.service;

import java.time.LocalDate;
import java.util.List;
import com.dimitri.avalo.entity.Asistencia;
import com.dimitri.avalo.dto.AsistenciaDTO;

public interface AsistenciaService {
    Asistencia registrar(AsistenciaDTO dto);
    List<Asistencia> listarEntreFechas(LocalDate desde, LocalDate hasta);
    void eliminar(Long id);
	Asistencia actualizar(Long id, AsistenciaDTO dto);
}
