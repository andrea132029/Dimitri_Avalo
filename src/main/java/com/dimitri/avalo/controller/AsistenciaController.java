package com.dimitri.avalo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dimitri.avalo.dto.AsistenciaDTO;
import com.dimitri.avalo.dto.AsistenciaResponseDTO;
import com.dimitri.avalo.entity.Asistencia;
import com.dimitri.avalo.service.AsistenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asistencias")
@Tag(name = "Asistencias", description = "Operaciones relacionadas con el registro de asistencias de familias")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    // --- POST ---
    @Operation(summary = "Registra una nueva asistencia para una familia")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Asistencia registrada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AsistenciaResponseDTO.class))),
        @ApiResponse(responseCode = "409", description = "Ya existe una asistencia para esa familia en esa fecha",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(mediaType = "application/json"))
    })
 
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody @Valid AsistenciaDTO dto) {
        try {
            Asistencia asistencia = asistenciaService.registrar(dto);
            AsistenciaResponseDTO response = new AsistenciaResponseDTO(asistencia);
            response.addLinks(asistencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", e.getMessage(),
                    "codigo", 409,
                    "fecha", LocalDate.now()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Error interno del servidor",
                    "detalle", e.getMessage()
            ));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una asistencia existente")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody @Valid AsistenciaDTO dto) {

        Asistencia asistencia = asistenciaService.actualizar(id, dto);

        AsistenciaResponseDTO response = new AsistenciaResponseDTO(asistencia);
        response.addLinks(asistencia);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtiene todas las asistencias registradas dentro de un rango de fechas")
    @ApiResponse(responseCode = "200", description = "Listado de asistencias encontradas",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AsistenciaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<AsistenciaResponseDTO>> listar(
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta) {

        List<AsistenciaResponseDTO> lista = asistenciaService.listarEntreFechas(desde, hasta)
                .stream()
                .map(a -> {
                    AsistenciaResponseDTO dto = new AsistenciaResponseDTO(a);
                    dto.addLinks(a);
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    // --- DELETE ---
    @Operation(summary = "Elimina (marca como inactiva) una asistencia por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Asistencia eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Asistencia no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
}
