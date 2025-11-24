package com.dimitri.avalo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimitri.avalo.dto.RecetaSimpleDTO;
import com.dimitri.avalo.dto.RecetaSimpleRequestDTO;
import com.dimitri.avalo.entity.RecetaSimple;
import com.dimitri.avalo.exception.Excepcion;
import com.dimitri.avalo.repository.RecetaSimpleRepository;
import com.dimitri.avalo.service.RecetaSimpleService;

@Service
public class RecetaSimpleServiceImpl implements RecetaSimpleService {

    @Autowired
    private RecetaSimpleRepository repo;

    private RecetaSimpleDTO toDTO(RecetaSimple r) {
        RecetaSimpleDTO dto = new RecetaSimpleDTO();
        dto.id = r.getId();
        dto.nombre = r.getNombre();
        dto.pesoRacion = r.getPesoRacion();
        dto.caloriasRacion = r.getCaloriasRacion();

        dto.linkPreparaciones = "/raciones?recetaId=" + r.getId();

        return dto;
    }

    @Override
    public RecetaSimpleDTO crear(RecetaSimpleRequestDTO dto) {

        if (repo.existsByNombre(dto.nombre))
            throw new Excepcion("Ya existe una receta con ese nombre", null);

        RecetaSimple r = new RecetaSimple();
        r.setNombre(dto.nombre);
        r.setPesoRacion(dto.pesoRacion);
        r.setCaloriasRacion(dto.caloriasRacion);
        r.setActiva(true);

        repo.save(r);
        return toDTO(r);
    }

    @Override
    public RecetaSimpleDTO obtener(Long id) {
        RecetaSimple r = repo.findById(id)
                .orElseThrow(() -> new Excepcion("Receta no encontrada"));
        return toDTO(r);
    }

    @Override
    public List<RecetaSimpleDTO> listar() {
        return repo.findAll().stream().filter(r -> r.getActiva()).map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RecetaSimpleDTO modificar(Long id, RecetaSimpleRequestDTO dto) {
        RecetaSimple r = repo.findById(id)
                .orElseThrow(() -> new Excepcion("Receta no encontrada"));

        r.setNombre(dto.nombre);
        r.setPesoRacion(dto.pesoRacion);
        r.setCaloriasRacion(dto.caloriasRacion);

        repo.save(r);
        return toDTO(r);
    }

    @Override
    public void eliminar(Long id) {
        RecetaSimple r = repo.findById(id)
                .orElseThrow(() -> new Excepcion("Receta no encontrada"));

        r.setActiva(false);
        repo.save(r);
    }
}
