package com.dimitri.avalo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dimitri.avalo.dto.FamiliaDTO;
import com.dimitri.avalo.dto.FamiliaRequestDTO;
import com.dimitri.avalo.dto.FamiliaResponseDTO;
import com.dimitri.avalo.entity.Familia;
import com.dimitri.avalo.service.FamiliaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/familias")
@CrossOrigin(origins = "*")
public class FamiliaController {

    @Autowired
    private FamiliaService service;

    @GetMapping
    public List<FamiliaDTO> listar() {
        return service.listarActivas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una familia por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Familia encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = FamiliaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Familia no encontrada")
    })
    public ResponseEntity<FamiliaResponseDTO> getFamiliaById(@PathVariable Long id) {
        Familia familia = service.obtenerPorId(id);
        if (familia == null) {
            return ResponseEntity.notFound().build();
        }
        FamiliaResponseDTO dto = new FamiliaResponseDTO(familia);
        dto.addSelfLink();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<FamiliaDTO> crear(@RequestBody FamiliaRequestDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamiliaDTO> modificar(@PathVariable Long id, @RequestBody FamiliaRequestDTO dto) {
        return ResponseEntity.ok(service.modificar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
