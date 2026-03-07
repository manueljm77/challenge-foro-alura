package com.alura.alura_foro.dto;



import com.alura.alura_foro.modelo.Topico;
import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String estado,
        Long autorId,
        Long cursoId,
        LocalDateTime fechaCreacion
) {
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getEstado().toString(),
                topico.getAutor().getId(),
                topico.getCurso().getId(),
                topico.getFechaCreacion());
    }
}


