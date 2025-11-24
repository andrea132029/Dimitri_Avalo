package com.dimitri.avalo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReporteService {

    // Historial de preparaciones por fecha
    List<Map<String, Object>> historialPreparaciones(LocalDate desde, LocalDate hasta);

    // Entregas agrupadas por familia
    List<Map<String, Object>> entregasPorFamilia(LocalDate desde, LocalDate hasta);

    // Estado actual del stock
    List<Map<String, Object>> estadoStock();
}
