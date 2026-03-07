package com.alura.alura_foro.servicio;

import com.alura.alura_foro.dto.DatosActualizarTopico;
import com.alura.alura_foro.dto.DatosListadoTopico;
import com.alura.alura_foro.dto.DatosRegistroTopico;
import com.alura.alura_foro.dto.DatosRespuestaTopico;
import com.alura.alura_foro.modelo.Topico;
import com.alura.alura_foro.modelo.Usuario;
import com.alura.alura_foro.modelo.Curso;
import com.alura.alura_foro.repository.TopicoRepository;
import com.alura.alura_foro.repository.UsuarioRepository;
import com.alura.alura_foro.repository.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Importación necesaria
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(TopicoRepository topicoRepository,
                         UsuarioRepository usuarioRepository,
                         CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    @Transactional
    public Topico crearTopico(DatosRegistroTopico datos) {
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionDeIntegridad("Ya existe un tópico con el mismo título y mensaje");
        }

        Usuario autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new ValidacionDeIntegridad(
                        "El autor con ID " + datos.autorId() + " no fue encontrado"));

        Curso curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new ValidacionDeIntegridad(
                        "El curso con ID " + datos.cursoId() + " no fue encontrado"));

        Topico topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        return topicoRepository.save(topico);
    }




    @Transactional
    public DatosRespuestaTopico actualizarTopico(Long id, DatosActualizarTopico datos) {
        // Buscamos usando el ID que viene de la URL
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El tópico con ID " + id + " no fue encontrado"));

        topico.actualizarDatos(datos);

        return new DatosRespuestaTopico(topico);
    }



    public Page<DatosListadoTopico> listarTopicos(String nombreCurso, Integer anio, Pageable paginacion) {
        // Si nos envían ambos parámetros, usamos la búsqueda personalizada
        if (nombreCurso != null && anio != null) {
            return topicoRepository.findByCursoAndAnio(nombreCurso, anio, paginacion)
                    .map(DatosListadoTopico::new);
        }
        // Si no nos envían parámetros, devolvemos todos como antes
        return topicoRepository.findAll(paginacion).map(DatosListadoTopico::new);
    }
    // No olvides importar: com.alura.alura_foro.dto.DatosRespuestaTopico;

    public DatosRespuestaTopico detallarTopico(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El tópico con ID " + id + " no fue encontrado"));

        // Reutilizamos el DTO que creaste para la respuesta de registro
        return new DatosRespuestaTopico(topico);
    }
        public void eliminarTopico(Long id) {
            Topico topico = topicoRepository.findById(id)
                    .orElseThrow(() -> new ValidacionDeIntegridad("El tópico con ID " + id + " no fue encontrado"));

            topicoRepository.delete(topico);
        }

}