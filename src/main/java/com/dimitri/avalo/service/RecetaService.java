package com.dimitri.avalo.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dimitri.avalo.dto.IngredienteDTO;
import com.dimitri.avalo.dto.PreparacionDTO;
import com.dimitri.avalo.dto.PreparacionRequestDTO;
import com.dimitri.avalo.dto.RecetaDTO;
import com.dimitri.avalo.dto.RecetaRequestDTO;
import com.dimitri.avalo.entity.Estacion;
import com.dimitri.avalo.entity.Ingrediente;
import com.dimitri.avalo.entity.Receta;
import com.dimitri.avalo.entity.TipoIngrediente;
@Service
public interface RecetaService {

	    List<Receta> listarRecetas();
	    List<Receta> filtrarPorCalorias(int min, int max);
	    List<Receta> filtrarPorNombreYCalorias(String nombre, int min, int max);
	    Receta modificarReceta(Long id, Receta datos);
	    void eliminarReceta(Long id);
	    void prepararReceta(Long id);
	    List<Receta> filtrar(String nombre, Float minCalorias, Float maxCalorias);
	    List<Receta> filtrarPorNombre(String nombre);
		Optional<Receta> buscarPorId(Long id);
		List<Ingrediente> buscarIngredientes(String nombre, Estacion estacion, TipoIngrediente tipo);
		Object obtenerIngredientesActivos();
		void guardarIngrediente(IngredienteDTO dto);
		RecetaDTO crearDesdeDTO(RecetaRequestDTO dto);
		PreparacionDTO prepararReceta(PreparacionRequestDTO dto);
		PreparacionDTO prepararReceta(Long recetaId, int raciones);
		
	  


	    
}
