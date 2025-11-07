package com.dimitri.avalo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.dimitri.avalo.dto.IngredienteDTO;
import com.dimitri.avalo.entity.Estacion;
import com.dimitri.avalo.entity.TipoIngrediente;
import com.dimitri.avalo.service.IngredienteService;

@RestController
@RequestMapping("/api/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @PostMapping
    public IngredienteDTO crear(@RequestBody IngredienteDTO dto) {
        return ingredienteService.guardar(dto);
    }

    @PutMapping("/{id}")
    public IngredienteDTO actualizar(@PathVariable Long id, @RequestBody IngredienteDTO dto) {
        return ingredienteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ingredienteService.eliminar(id);
    }

    @GetMapping
    public List<IngredienteDTO> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Estacion estacion,
            @RequestParam(required = false) TipoIngrediente tipo) {
        return ingredienteService.buscar(nombre, estacion, tipo);
    }

    @GetMapping("/{id}")
    public IngredienteDTO obtener(@PathVariable Long id) {
        return ingredienteService.buscarPorId(id);
    }
}
