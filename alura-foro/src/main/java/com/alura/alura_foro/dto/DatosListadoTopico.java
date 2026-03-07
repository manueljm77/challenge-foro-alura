package com.alura.alura_foro.dto;



import com.alura.alura_foro.modelo.Topico;
import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String estado,
        String autor,
        String curso
) {
    // Constructor que transforma la entidad Topico en este DTO
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado().toString(),
                topico.getAutor().getNombre(), // Extraemos el nombre directamente
                topico.getCurso().getNombre()  // Extraemos el nombre directamente
        );
    }
}
