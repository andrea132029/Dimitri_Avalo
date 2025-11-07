package com.dimitri.avalo.service;

import java.util.List;

import com.dimitri.avalo.dto.IngredienteDTO;
import com.dimitri.avalo.entity.Estacion;
import com.dimitri.avalo.entity.TipoIngrediente;

public interface IngredienteService {

    IngredienteDTO guardar(IngredienteDTO dto);
    IngredienteDTO actualizar(Long id, IngredienteDTO dto);
    void eliminar(Long id);

    List<IngredienteDTO> listar();
    List<IngredienteDTO> buscar(String nombre, Estacion estacion, TipoIngrediente tipo);

    IngredienteDTO buscarPorId(Long id);
}
