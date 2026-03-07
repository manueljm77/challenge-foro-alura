package com.alura.alura_foro.controller;

import com.alura.alura_foro.dto.DatosAutenticacionUsuario;
import com.alura.alura_foro.dto.DatosJWTToken;
import com.alura.alura_foro.modelo.Usuario;
import com.alura.alura_foro.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacion) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacion.login(),
                datosAutenticacion.clave()
        );

        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        // Generamos el token extrayendo el usuario autenticado
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        // Devolvemos el DTO con el token en la respuesta
        return ResponseEntity.ok(new DatosJWTToken(jwtToken));
    }
}