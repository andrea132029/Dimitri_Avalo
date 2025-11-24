package com.dimitri.avalo.service.impl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dimitri.avalo.dto.IngredienteDTO;
import com.dimitri.avalo.dto.IngredienteResumenDTO;
import com.dimitri.avalo.dto.PreparacionDTO;
import com.dimitri.avalo.dto.PreparacionRequestDTO;
import com.dimitri.avalo.dto.RecetaDTO;
import com.dimitri.avalo.dto.RecetaRequestDTO;
import com.dimitri.avalo.entity.Condimento;
import com.dimitri.avalo.entity.Estacion;
import com.dimitri.avalo.entity.Ingrediente;
import com.dimitri.avalo.entity.ItemReceta;
import com.dimitri.avalo.entity.Preparacion;
import com.dimitri.avalo.entity.Producto;
import com.dimitri.avalo.entity.Receta;
import com.dimitri.avalo.entity.RegistroPreparacion;
import com.dimitri.avalo.entity.TipoIngrediente;
import com.dimitri.avalo.exception.Excepcion;
import com.dimitri.avalo.repository.IngredienteRepository;
import com.dimitri.avalo.repository.ItemRecetaRepository;
import com.dimitri.avalo.repository.PreparacionRepository;
import com.dimitri.avalo.repository.ProductoRepository;
import com.dimitri.avalo.repository.RecetaRepository;
import com.dimitri.avalo.repository.RegistroPreparacionRepository;
import com.dimitri.avalo.service.RecetaService;

import jakarta.transaction.Transactional;

@Service
public class RecetaServiceImpl implements RecetaService { 
    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private ItemRecetaRepository itemRecetaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private RegistroPreparacionRepository registroPreparacionRepository;
    @Autowired 
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PreparacionRepository preparacionRepository;

    @Override
    public List<Receta> listarRecetas() {
        return recetaRepository.findByActivaTrue();
    }

    public Optional<Receta> buscarPorId(Long id) {
       return recetaRepository.findByIdAndActivaTrue(id);
    }

    
    @Transactional
    public Receta modificarReceta(Long id, Receta datosActualizados) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new Excepcion("Receta no encontrada"));

        receta.setDescripcion(datosActualizados.getDescripcion());
//marca los items antiguos como inactivos
        for (ItemReceta item : receta.getItems()) {
            item.setActiva(false); // la eliminacion de receta 
            
        }

//agrega nuevos items
        for (ItemReceta item : datosActualizados.getItems()) {
            item.setReceta(receta);
            
            
            // Descontar stock si es Producto
            if (item.getIngrediente() instanceof Producto producto) {
                Float nuevoStock = producto.getStockDisponible() - item.getCantidad();
                if (nuevoStock < 0) {
                    throw new Excepcion("No hay stock suficiente para el producto: " + producto.getNombre());
                }
                producto.descontarStock(item.getCantidad());
                 productoRepository.save(producto); // Guardamos el producto con el nuevo stock
            }

            receta.getItems().add(item);
        }

        return recetaRepository.save(receta);
    }

    @Transactional
    public void eliminarReceta(Long id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new Excepcion("Receta no encontrada"));

        receta.setActiva(false);
        recetaRepository.save(receta);
    }
    public Float calcularCaloriasTotales(Receta receta) {
        return receta.getItems().stream()
            .filter(ItemReceta::getActiva)
            .map(item -> item.getIngrediente().getCalorias() * item.getCantidad())
            .reduce(0f, Float::sum);
    }

    public List<Receta> filtrarPorCalorias(Float minCalorias, Float maxCalorias) {
        return listarRecetas().stream()
            .filter(receta -> {
                Float total = calcularCaloriasTotales(receta);
                return total >= minCalorias && total <= maxCalorias;
            })
            .toList();
    }

    public List<Receta> filtrarPorNombreYCalorias(String nombre, Float minCalorias, Float maxCalorias) {
        return listarRecetas().stream()
            .filter(receta -> receta.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .filter(receta -> {
                Float total = calcularCaloriasTotales(receta);
                return total >= minCalorias && total <= maxCalorias;
            })
            .toList();
    }

    @Transactional
    public void prepararReceta(Long recetaId) {
        Receta receta = recetaRepository.findByIdAndActivaTrue(recetaId)
            .orElseThrow(() -> new Excepcion("Receta no encontrada o inactiva"));

        for (ItemReceta item : receta.getItems()) {
            Ingrediente ingrediente = item.getIngrediente();

            if (!(ingrediente instanceof Producto producto)) {
                throw new Excepcion("El ingrediente " + ingrediente.getNombre() + " no es un producto y no se puede descontar stock");
            }

            Float stockADescontar = item.getCantidad();
            if (producto.getStockDisponible() < stockADescontar) {
                throw new Excepcion("No hay stock suficiente para el producto: " + producto.getNombre());
            }

            producto.descontarStock(stockADescontar);
            productoRepository.save(producto);
        }

        registroPreparacionRepository.save(new RegistroPreparacion(receta, 1));
    }

    @Override
    public List<Receta> filtrar(String nombre, Float minCalorias, Float maxCalorias) {
        return listarRecetas().stream()
            .filter(r -> nombre == null || nombre.isBlank() || r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .filter(r -> {
                Float calorias = calcularCaloriasTotales(r);
                return (minCalorias == null || calorias >= minCalorias)
                    && (maxCalorias == null || calorias <= maxCalorias);
            })
            .toList();
    }


    public List<Receta> filtrarPorNombre(String nombre) {
        return listarRecetas().stream()
            .filter(r -> r.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .toList();
    }
 // ==========================
    // INGREDIENTES
    // ==========================

    @Override
    public void guardarIngrediente(IngredienteDTO dto) {
        if (ingredienteRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new Excepcion("Ya existe un ingrediente con ese nombre: " + dto.getNombre());
        }

        if (TipoIngrediente.PRODUCTO.equals(dto.getTipo())) {
            if (dto.getStockDisponible() == null || dto.getStockDisponible() < 0) {
                throw new Excepcion("El stock no puede ser nulo ni negativo para un Producto.");
            }

            if (dto.getPrecioActual() == null || dto.getPrecioActual() <= 0) {
                throw new Excepcion("El precio debe ser mayor a cero para un Producto.");
            }

            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setCalorias(dto.getCalorias());
            producto.setEstacion(dto.getEstacion());
            producto.setStockDisponible(dto.getStockDisponible());
            producto.setPrecioActual(dto.getPrecioActual());
            producto.setActivo(true);

            ingredienteRepository.save(producto);

        } else if (TipoIngrediente.CONDIMENTO.equals(dto.getTipo())) {
            Condimento condimento = new Condimento();
            condimento.setNombre(dto.getNombre());
            condimento.setCalorias(dto.getCalorias());
            condimento.setEstacion(dto.getEstacion());
            condimento.setActivo(true);

            ingredienteRepository.save(condimento);

        } else {
            throw new Excepcion("Tipo de ingrediente no reconocido: " + dto.getTipo());
        }
    }

    @Override
    public List<Ingrediente> buscarIngredientes(String nombre, Estacion estacion, TipoIngrediente tipo) {
        return ingredienteRepository.findAll().stream()
                .filter(i -> i.getActivo() == null || i.getActivo())
                .filter(i -> nombre == null || i.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(i -> estacion == null || i.getEstacion() == estacion)
                .filter(i -> {
                    if (tipo == null) return true;
                    if (tipo == TipoIngrediente.PRODUCTO) return i instanceof Producto;
                    if (tipo == TipoIngrediente.CONDIMENTO) return i instanceof Condimento;
                    return false;
                })
                .toList();
    }

    @Override
    public Object obtenerIngredientesActivos() {
        return ingredienteRepository.findAll().stream()
                .filter(i -> i.getActivo() == null || i.getActivo())
                .collect(Collectors.toList());
    }

    public void actualizarIngrediente(IngredienteDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(dto.getId())
                .orElseThrow(() -> new Excepcion("Ingrediente no encontrado"));

        if (dto.getTipo() == TipoIngrediente.PRODUCTO && !(ingrediente instanceof Producto)) {
            ingredienteRepository.delete(ingrediente);

            Producto nuevo = new Producto();
            nuevo.setId(dto.getId());
            nuevo.setNombre(dto.getNombre());
            nuevo.setCalorias(dto.getCalorias());
            nuevo.setEstacion(dto.getEstacion());
            nuevo.setStockDisponible(dto.getStockDisponible());
            nuevo.setPrecioActual(dto.getPrecioActual());
            nuevo.setActivo(true);

            ingredienteRepository.save(nuevo);
            return;
        }

        if (dto.getTipo() == TipoIngrediente.CONDIMENTO && !(ingrediente instanceof Condimento)) {
            ingredienteRepository.delete(ingrediente);

            Condimento nuevo = new Condimento();
            nuevo.setId(dto.getId());
            nuevo.setNombre(dto.getNombre());
            nuevo.setCalorias(dto.getCalorias());
            nuevo.setEstacion(dto.getEstacion());
            nuevo.setActivo(true);

            ingredienteRepository.save(nuevo);
            return;
        }

        ingrediente.setNombre(dto.getNombre());
        ingrediente.setCalorias(dto.getCalorias());
        ingrediente.setEstacion(dto.getEstacion());
        ingrediente.setActivo(true);

        if (ingrediente instanceof Producto producto) {
            producto.setStockDisponible(dto.getStockDisponible());
            producto.setPrecioActual(dto.getPrecioActual());
        }

        ingredienteRepository.save(ingrediente);
    }
    
    
    @Override
    @Transactional
    public RecetaDTO crearDesdeDTO(RecetaRequestDTO dto) {
    	  if (recetaRepository.findByNombre(dto.getNombre()).isPresent()) {
    	        throw new Excepcion("Ya existe una receta con ese nombre");
    	    }
        Receta receta = new Receta();
        receta.setNombre(dto.getNombre());
        receta.setDescripcion(dto.getDescripcion());
        receta.setActiva(true);

        // Agregarmos los items con sus ingredientes reales
        dto.getItems().forEach(itemDTO -> {
            Ingrediente ingrediente = ingredienteRepository.findById(itemDTO.getIngredienteId())
                    .orElseThrow(() -> new Excepcion("Ingrediente no encontrado"));

            ItemReceta item = new ItemReceta();
            item.setIngrediente(ingrediente);
            item.setCantidad(itemDTO.getCantidad());
            item.setCalorias(ingrediente.getCalorias()); 
            item.setActiva(true);
            item.setReceta(receta);

            receta.getItems().add(item);

            // Manejo de stock si es producto
            if (ingrediente instanceof Producto producto) {
                Float nuevoStock = producto.getStockDisponible() - itemDTO.getCantidad();
                if (nuevoStock < 0) {
                    throw new Excepcion("Stock insuficiente: " + producto.getNombre());
                }
                producto.setStockDisponible(nuevoStock);
                productoRepository.save(producto);
            }
        });

        Receta guardada = recetaRepository.save(receta);

        //  Convertimos a RecetaDTO para la respuesta
        RecetaDTO respuesta = new RecetaDTO();
        respuesta.setId(guardada.getId());
        respuesta.setNombre(guardada.getNombre());
        respuesta.setCaloriasTotales((float) calcularCaloriasTotales(guardada));

        respuesta.setIngredientes(
                guardada.getItems().stream().map(item -> {
                    return new IngredienteResumenDTO(
                            item.getIngrediente().getId(),
                            item.getIngrediente().getNombre(),
                            item.getCantidad()
                    );
                }).toList()
        );

        return respuesta;
    }
    
    @Override
    @Transactional
    public PreparacionDTO prepararReceta(Long recetaId, int raciones) {

        // Buscar receta activa
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new Excepcion("Receta no encontrada"));

        // Validar raciones
        if (raciones <= 0) {
            throw new Excepcion("Las raciones deben ser mayor a 0");
        }

        //  Verifica stock de todos los ingredientes ANTES de restar
        for (ItemReceta item : receta.getItems()) {
            Ingrediente ing = item.getIngrediente();

            if (ing instanceof Producto prod) {
                float requerido = item.getCantidad() * raciones;

                if (prod.getStockDisponible() < requerido) {
                    throw new Excepcion("Stock insuficiente para: " + prod.getNombre());
                }
            }
        }

        //  Ahora restar stock (ya validado)
        for (ItemReceta item : receta.getItems()) {
            Ingrediente ing = item.getIngrediente();

            if (ing instanceof Producto prod) {
                float requerido = item.getCantidad() * raciones;
                prod.setStockDisponible(prod.getStockDisponible() - requerido);
                ingredienteRepository.save(prod);
            }
        }

        // Registrar la preparación
        Preparacion p = new Preparacion();
        p.setReceta(receta);
        p.setCantidadRaciones(raciones);
        p.setFechaPreparacion(LocalDate.now());
        p.setActiva(true);
        preparacionRepository.save(p);

        // Transformar a DTO para respuesta
        PreparacionDTO dto = new PreparacionDTO();
        dto.setId(p.getId());
        dto.setRecetaId(receta.getId());
        dto.setRecetaNombre(receta.getNombre());
        dto.setRacionesGeneradas(p.getCantidadRaciones());
        dto.setFechaPreparacion(p.getFechaPreparacion());

        return dto;
    }

    @Override
    @Transactional
    public PreparacionDTO prepararReceta(PreparacionRequestDTO dto) {
        return prepararReceta(dto.getRecetaId(), dto.getRaciones());
    }

	@Override
	public List<Receta> filtrarPorCalorias(int min, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Receta> filtrarPorNombreYCalorias(String nombre, int min, int max) {
		// TODO Auto-generated method stub
		return null;
	}


}





