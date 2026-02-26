
package com.alura.alura_foro.modelo;

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

    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")  // ← Mapea a la columna real en tu BD
    private EstadoTopico estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // ✅ Constructor principal
    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoTopico.ABIERTO;
    }

    // ✅ Constructor JPA)
    public Topico() {
    }


    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public EstadoTopico getEstado() { return estado; }
    public Usuario getAutor() { return autor; }
    public Curso getCurso() { return curso; }

    // ✅ Setters mínimos necessarily
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setEstado(EstadoTopico estado) { this.estado = estado; }
}
