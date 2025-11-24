package com.dimitri.avalo.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimitri.avalo.repository.EntregaRepository;
import com.dimitri.avalo.repository.PreparacionRepository;
import com.dimitri.avalo.repository.ProductoRepository;
import com.dimitri.avalo.service.ReporteService;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private PreparacionRepository prepRepo;

    @Autowired
    private EntregaRepository entregaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    // Historial de preparaciones
    @Override
    public List<Map<String, Object>> historialPreparaciones(LocalDate desde, LocalDate hasta) {
        return prepRepo.findAll().stream()
                .filter(p -> p.getFechaPreparacion() != null
                        && !p.getFechaPreparacion().isBefore(desde)
                        && !p.getFechaPreparacion().isAfter(hasta))
                .map(p -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("fecha", p.getFechaPreparacion());
                    data.put("recetaId", p.getReceta() != null ? p.getReceta().getId() : null);
                    data.put("recetaNombre", p.getReceta() != null ? p.getReceta().getNombre() : null);
                    data.put("raciones", p.getCantidadRaciones());
                    return data;
                })
                .collect(Collectors.toList());
    }

    // Entregas por familia
    @Override
    public List<Map<String, Object>> entregasPorFamilia(LocalDate desde, LocalDate hasta) {
        return entregaRepo.findAll().stream()
                .filter(e -> e.getFechaEntrega() != null
                        && !e.getFechaEntrega().isBefore(desde)
                        && !e.getFechaEntrega().isAfter(hasta))
                .map(e -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("fechaEntrega", e.getFechaEntrega());
                    data.put("familiaId", e.getFamilia() != null ? e.getFamilia().getId() : null);
                    data.put("nombreFamilia", e.getFamilia() != null ? e.getFamilia().getNombre() : null);
                    data.put("racionesEntregadas", e.getRacionesEntregadas());
                    return data;
                })
                .collect(Collectors.toList());
    }

    //  Estado del stock
    @Override
    public List<Map<String, Object>> estadoStock() {
        return productoRepo.findAll().stream()
                .map(p -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", p.getId());
                    data.put("nombre", p.getNombre());
                    data.put("stockDisponible", p.getStockDisponible());
                    data.put("precioActual", p.getPrecioActual());
                    return data;
                })
                .collect(Collectors.toList());
    }
}


