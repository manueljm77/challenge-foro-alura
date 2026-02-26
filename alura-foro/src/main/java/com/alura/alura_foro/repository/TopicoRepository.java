package com.alura.alura_foro.repository;



import com.alura.alura_foro.modelo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Verificar si ya existe un tópico con mismo título y mensaje
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);
}