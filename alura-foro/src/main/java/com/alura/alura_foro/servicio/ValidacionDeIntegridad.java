package com.alura.alura_foro.servicio;



public class ValidacionDeIntegridad extends RuntimeException {
    public ValidacionDeIntegridad(String mensaje) {
        super(mensaje);
    }
}