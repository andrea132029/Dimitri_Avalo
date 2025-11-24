package com.dimitri.avalo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimitri.avalo.entity.Entrega;
import com.dimitri.avalo.entity.Integrante;
import com.dimitri.avalo.entity.Preparacion;
import com.dimitri.avalo.repository.EntregaRepository;
import com.dimitri.avalo.service.EntregaService;
import com.dimitri.avalo.service.PreparacionService;

@Service
public class EntregaServiceImpl implements EntregaService {

    @Autowired
    private EntregaRepository entregaRepo;

    @Autowired
    private PreparacionService prepService;

    @Override
    public Entrega registrar(Entrega e) {
        // Valida duplicidad
        if (entregaRepo.existsByFamiliaIdAndFechaEntregaAndActivoTrue(
                e.getFamilia().getId(), e.getFechaEntrega())) {
            throw new IllegalArgumentException("Ya existe una entrega para esa familia en esta fecha");
        }

        // Obtener la preparación y familia
        Preparacion prep = prepService.buscarPorId(e.getPreparacion().getId());
        var familia = e.getFamilia();

        // Calcular automáticamente la cantidad de raciones = integrantes activos
        long integrantesActivos = familia.getIntegrantes().stream()
                .filter(Integrante::isActivo)
                .count();

        if (integrantesActivos == 0) {
            throw new IllegalArgumentException("La familia no tiene integrantes activos");
        }

        e.setRacionesEntregadas((int) integrantesActivos);

        // Validar stock suficiente
        if (prep.getCantidadRaciones() < e.getRacionesEntregadas()) {
            throw new IllegalArgumentException("No hay suficientes raciones en la preparación");
        }

        // Descontar raciones
        prep.setCantidadRaciones(prep.getCantidadRaciones() - e.getRacionesEntregadas());
        prepService.guardarPreparacion(prep);

        //  Registrar entrega
        e.setActivo(true);
        e.setFechaEntrega(LocalDate.now());
        return entregaRepo.save(e);
    }

    @Override
    public void eliminar(Long id) {
        Entrega e = entregaRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entrega no encontrada"));
        e.setActivo(false);
        entregaRepo.save(e);

        // Restaurar raciones a la preparación
        Preparacion prep = e.getPreparacion();
        prep.setCantidadRaciones(prep.getCantidadRaciones() + e.getRacionesEntregadas());
        prepService.guardarPreparacion(prep);
    }

    @Override
    public Entrega buscarPorId(Long id) {
        return entregaRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Entrega no encontrada"));
    }

    @Override
    public List<Entrega> listarPorFecha(LocalDate fecha) {
        return entregaRepo.findAllByFechaEntregaAndActivoTrue(fecha);
    }

    @Override
    public List<Entrega> listarPorFechaYFamilia(LocalDate fecha, Long familiaId) {
        return entregaRepo.findAllByFechaEntregaAndFamiliaIdAndActivoTrue(fecha, familiaId);
    }

    @Override
    public List<Entrega> listarPorFechaYNombreFamilia(LocalDate fecha, String nombreFamilia) {
        return entregaRepo.findAllByFechaEntregaAndFamiliaNombreContainingIgnoreCaseAndActivoTrue(fecha, nombreFamilia);
    }
}
