package com.alura.alura_foro.repository;



import com.alura.alura_foro.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso AND YEAR(t.fechaCreacion) = :anio")
    Page<Topico> findByCursoAndAnio(String nombreCurso, Integer anio, Pageable paginacion);

    // Verificar si ya existe un tópico con mismo título y mensaje
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

}