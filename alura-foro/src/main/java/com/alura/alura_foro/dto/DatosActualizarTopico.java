package com.alura.alura_foro.dto;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull


        String titulo,
        String mensaje

) {
}
