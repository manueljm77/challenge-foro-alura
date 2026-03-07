package com.alura.alura_foro.controller;


import com.alura.alura_foro.dto.DatosActualizarTopico;
import com.alura.alura_foro.dto.DatosListadoTopico;
import com.alura.alura_foro.dto.DatosRegistroTopico;
import com.alura.alura_foro.dto.DatosRespuestaTopico;
import com.alura.alura_foro.modelo.Topico;
import com.alura.alura_foro.servicio.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriBuilder) {

        Topico topico = topicoService.crearTopico(datos);
        DatosRespuestaTopico respuesta = new DatosRespuestaTopico(topico);
        URI url = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @RequestParam(required = false) String nombreCurso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {

        return ResponseEntity.ok(topicoService.listarTopicos(nombreCurso, anio, paginacion));
    }
    // ✅ ELIMINAMOS LA LLAVE EXTRA QUE ESTABA AQUÍ

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> detallarTopico(@PathVariable Long id) {
        var topicoDetalle = topicoService.detallarTopico(id);
        return ResponseEntity.ok(topicoDetalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos) {

        var respuesta = topicoService.actualizarTopico(id, datos);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
} // ✅ ESTA ES LA ÚNICA LLAVE QUE DEBE CERRAR LA CLASE

