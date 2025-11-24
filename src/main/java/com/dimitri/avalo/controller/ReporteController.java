package com.dimitri.avalo.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dimitri.avalo.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/preparaciones")
    public ResponseEntity<?> historialPreparaciones(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(reporteService.historialPreparaciones(desde, hasta));
    }

    @GetMapping("/entregas")
    public ResponseEntity<?> entregasPorFamilia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(reporteService.entregasPorFamilia(desde, hasta));
    }

    @GetMapping("/stock")
    public ResponseEntity<?> estadoStock() {
        return ResponseEntity.ok(reporteService.estadoStock());
    }
    
 //  Resumen general
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumenGeneral(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

        Map<String, Object> resumen = new LinkedHashMap<>();

        resumen.put("preparaciones", reporteService.historialPreparaciones(desde, hasta));
        resumen.put("entregas", reporteService.entregasPorFamilia(desde, hasta));
        resumen.put("stock", reporteService.estadoStock());

        return ResponseEntity.ok(resumen);
    }

}
