package com.alura.alura_foro.servicio;

import com.alura.alura_foro.dto.DatosRegistroTopico;
import com.alura.alura_foro.modelo.Topico;
import com.alura.alura_foro.modelo.Usuario;
import com.alura.alura_foro.modelo.Curso;
import com.alura.alura_foro.repository.TopicoRepository;
import com.alura.alura_foro.repository.UsuarioRepository;
import com.alura.alura_foro.repository.CursoRepository;
import org.springframework.stereotype.Service;

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

    public Topico crearTopico(DatosRegistroTopico datos) {
        // Validar duplicados
        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            throw new ValidacionDeIntegridad("Ya existe un tópico con el mismo título y mensaje");
        }

        // Validar que existan autor y curso
        Usuario autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new ValidacionDeIntegridad(
                        "El autor con ID " + datos.autorId() + " no fue encontrado"));

        Curso curso = cursoRepository.findById(datos.cursoId())
                .orElseThrow(() -> new ValidacionDeIntegridad(
                        "El curso con ID " + datos.cursoId() + " no fue encontrado"));

        Topico topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
        return topicoRepository.save(topico);
    }
}