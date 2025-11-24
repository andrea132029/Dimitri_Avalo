package com.dimitri.avalo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dimitri.avalo.dto.PreparacionDTO;
import com.dimitri.avalo.dto.PreparacionRequestDTO;
import com.dimitri.avalo.dto.RecetaDTO;
import com.dimitri.avalo.dto.RecetaRequestDTO;
import com.dimitri.avalo.entity.Receta;
import com.dimitri.avalo.service.RecetaService;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {
                              
    @Autowired
    private RecetaService recetaService;

    //enpoint
    
    @PostMapping
    public ResponseEntity<RecetaDTO> crearReceta(@RequestBody RecetaRequestDTO dto) {
        RecetaDTO resultado = recetaService.crearDesdeDTO(dto);
        return ResponseEntity.ok(resultado);
    }
    @PostMapping("/{id}/preparar")
    public ResponseEntity<PreparacionDTO> preparar(
            @PathVariable Long id,
            @RequestParam int raciones) {
        return ResponseEntity.ok(recetaService.prepararReceta(id, raciones));
    }


  @DeleteMapping("/{id}")
  public void eliminarReceta(@PathVariable Long id) {
      recetaService.eliminarReceta(id);
  }


    @PutMapping("/{id}")
    public Receta actualizarReceta(@PathVariable Long id, @RequestBody Receta receta) {
        return recetaService.modificarReceta(id, receta);
    }

   

    @GetMapping
    public List<Receta> filtrar(@RequestParam(required = false) String nombre,
            @RequestParam(required = false) Float minCalorias,
            @RequestParam(required = false) Float maxCalorias) {
return recetaService.filtrar(nombre, minCalorias, maxCalorias);
}

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecetaById(@PathVariable Long id) {
        var receta = recetaService.buscarPorId(id);

        if (receta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(receta);
    }


    }

