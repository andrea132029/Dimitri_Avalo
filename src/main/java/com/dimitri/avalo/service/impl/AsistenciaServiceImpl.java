package com.dimitri.avalo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimitri.avalo.dto.AsistenciaDTO;
import com.dimitri.avalo.entity.Asistencia;
import com.dimitri.avalo.entity.Familia;
import com.dimitri.avalo.entity.Preparacion;
import com.dimitri.avalo.exception.Excepcion;
import com.dimitri.avalo.repository.AsistenciaRepository;
import com.dimitri.avalo.repository.FamiliaRepository;
import com.dimitri.avalo.repository.PreparacionRepository;
import com.dimitri.avalo.service.AsistenciaService;

@Service
public class AsistenciaServiceImpl implements AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepo;

    @Autowired
    private FamiliaRepository familiaRepo;
    @Autowired
    private PreparacionRepository preparacionRepo;

    
    @Override
    public Asistencia registrar(AsistenciaDTO dto) {
        // Validar duplicidad (una asistencia por familia por día)
        if (asistenciaRepo.existsByFamiliaIdAndFechaAndActivaTrue(dto.getIdFamilia(), dto.getFecha())) {
            throw new RuntimeException("Ya existe una asistencia registrada para esa familia en esa fecha");
        }

        Familia familia = familiaRepo.findById(dto.getIdFamilia())
                .orElseThrow(() -> new NoSuchElementException("Familia no encontrada"));

        Preparacion preparacion = preparacionRepo.findById(dto.getIdPreparacion())
                .orElseThrow(() -> new Excepcion("Preparación no encontrada"));
        
        // Validación: fecha no puede ser después del vencimiento
        if (dto.getFecha().isAfter(preparacion.getFechaVencimiento())) {
            throw new Excepcion("La fecha de entrega no puede ser posterior a la fecha de vencimiento de la preparación");
        }
      
        // Crear asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setFamilia(familia);
        asistencia.setPreparacion(preparacion);
        asistencia.setFecha(dto.getFecha());
        asistencia.setActiva(true);

        return asistenciaRepo.save(asistencia);
    }

    @Override
    public Asistencia actualizar(Long id, AsistenciaDTO dto) {

        Asistencia asistencia = asistenciaRepo.findById(id)
                .orElseThrow(() -> new Excepcion("Asistencia no encontrada", "id", 0));

        Familia familia = familiaRepo.findById(dto.getIdFamilia())
                .orElseThrow(() -> new Excepcion("Familia no encontrada", "familia", 0));

        Preparacion prep = preparacionRepo.findById(dto.getIdPreparacion())
                .orElseThrow(() -> new Excepcion("Preparación no encontrada", "preparacion", 0));

        if (dto.getFecha().isAfter(prep.getFechaVencimiento())) {
            throw new Excepcion("Fecha de entrega posterior al vencimiento", "fecha", 0);
        }

        asistencia.setFamilia(familia);
        asistencia.setPreparacion(prep);
        asistencia.setFecha(dto.getFecha());

        return asistenciaRepo.save(asistencia);
    }

    @Override
    public List<Asistencia> listarEntreFechas(LocalDate desde, LocalDate hasta) {
        return asistenciaRepo.findAllByFechaBetweenAndActivaTrue(desde, hasta);
    }

    @Override
    public void eliminar(Long id) {
        Asistencia a = asistenciaRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Asistencia no encontrada"));
        a.setActiva(false);
        asistenciaRepo.save(a);
    }
}
