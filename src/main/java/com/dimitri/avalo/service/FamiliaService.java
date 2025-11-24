package com.dimitri.avalo.service;
import java.util.List;

import com.dimitri.avalo.dto.FamiliaDTO;
import com.dimitri.avalo.dto.FamiliaRequestDTO;
import com.dimitri.avalo.entity.Familia;


public interface FamiliaService {
    
/*
	    Familia crearFamilia(Familia familia) throws Excepcion;
	    Familia actualizarFamilia(Long id, Familia familiaActualizada);
	    List<Familia> buscarPorNombre(String nombre);
	    List<Familia> listarTodas();
	    Familia buscarPorId(Long id);
	    void eliminarFamilia(Long id);
	    int contarIntegrantesActivos(Long familiaId);*/
	 

	        FamiliaDTO crear(FamiliaRequestDTO dto);
	        List<FamiliaDTO> listarActivas();
	        FamiliaDTO modificar(Long id, FamiliaRequestDTO dto);
	        void eliminar(Long id); // baja lógica
			int contarIntegrantesActivos(Long id);
			Familia obtenerPorId(Long id);

	    }



	
