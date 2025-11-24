package com.dimitri.avalo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimitri.avalo.dto.IngredienteDTO;
import com.dimitri.avalo.entity.Estacion;
import com.dimitri.avalo.entity.Ingrediente;
import com.dimitri.avalo.entity.TipoIngrediente;
import com.dimitri.avalo.exception.Excepcion;
import com.dimitri.avalo.repository.IngredienteRepository;
import com.dimitri.avalo.service.IngredienteService;

@Service
public class IngredienteServiceImpl implements IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Override
    public IngredienteDTO guardar(IngredienteDTO dto) {

        if (ingredienteRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new Excepcion("Ya existe un ingrediente con ese nombre");
        }

        Ingrediente entidad = dto.toEntidad();
        entidad.setActivo(true);
        ingredienteRepository.save(entidad);

        return new IngredienteDTO(entidad);
    }

    @Override
    public IngredienteDTO actualizar(Long id, IngredienteDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new Excepcion("Ingrediente no encontrado"));

        dto.setId(id);
        Ingrediente actualizado = dto.toEntidad();
        actualizado.setActivo(true);

        ingredienteRepository.save(actualizado);

        return new IngredienteDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new Excepcion("Ingrediente no encontrado"));

        ingrediente.setActivo(false);
        ingredienteRepository.save(ingrediente);
    }

    @Override
    public List<IngredienteDTO> listar() {
        return ingredienteRepository.findAll().stream()
                .filter(i -> i.getActivo() == null || i.getActivo())
                .map(IngredienteDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public IngredienteDTO buscarPorId(Long id) {
        return ingredienteRepository.findById(id)
                .filter(i -> i.getActivo())
                .map(IngredienteDTO::new)
                .orElseThrow(() -> new Excepcion("Ingrediente no encontrado"));
    }

    @Override
    public List<IngredienteDTO> buscar(String nombre, Estacion estacion, TipoIngrediente tipo) {
        return listar().stream()
                .filter(i -> nombre == null || i.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(i -> estacion == null || i.getEstacion() == estacion)
                .filter(i -> tipo == null || i.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}
