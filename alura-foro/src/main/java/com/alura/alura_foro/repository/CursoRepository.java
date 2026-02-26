package com.alura.alura_foro.repository;



import com.alura.alura_foro.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}