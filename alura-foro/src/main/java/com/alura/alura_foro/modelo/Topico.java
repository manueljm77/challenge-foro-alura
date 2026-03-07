
package com.alura.alura_foro.modelo;

import com.alura.alura_foro.dto.DatosActualizarTopico;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    // 1. Mapeamos explícitamente al nombre de la columna en MySQL
    @Column(name = "fecha_creacion") // ← Verifica si en tu BD es 'fecha' o 'fecha_creacion'
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EstadoTopico estado = EstadoTopico.ABIERTO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;



    // Constructor vacío requerido por JPA
    public Topico() {}

    // Constructor para tu Service
    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;

        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoTopico.ABIERTO;
    }




    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public EstadoTopico getEstado() { return estado; }
    public Usuario getAutor() { return autor; }
    public Curso getCurso() { return curso; }

    public void actualizarDatos(DatosActualizarTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }

    }
}