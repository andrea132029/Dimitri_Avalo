package com.dimitri.avalo.service;
import java.time.LocalDate;
import java.util.List;

import com.dimitri.avalo.entity.Preparacion;

public interface PreparacionService {

	 Preparacion registrarPreparacion(Preparacion preparacion);
	    List<Preparacion> listarPreparaciones();
	    List<Preparacion> listarPorFecha(LocalDate fecha);

	    Preparacion guardarPreparacion(Preparacion preparacion);
	    Preparacion modificarFechaPreparacion(Long id, Preparacion preparacionActualizada);
	    void eliminarPreparacion(Long id);
	    Preparacion buscarPorId(Long id);
	
}