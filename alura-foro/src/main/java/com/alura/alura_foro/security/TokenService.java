package com.alura.alura_foro.security;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.alura.alura_foro.modelo.Usuario;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro alura")
                    .withSubject(usuario.getUsername())// Identificamos a quién le pertenece el token
                    .withClaim("id", usuario.getId()) // Guardamos el ID por si lo necesitamos después
                    .withExpiresAt(generarFechaExpiracion()) // Le damos tiempo de vida
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error al generar el token JWT", exception);
        }


    }

    private Instant generarFechaExpiracion() {
        // El token expirará en 2 horas. El offset "-06:00" es para la hora de México/Centro, ajústalo si es necesario.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }



    // Agrega este método debajo de tu generarToken()
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("El token es nulo.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // Usa el mismo secreto para validar
            return JWT.require(algorithm)
                    .withIssuer("foro alura")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido o expirado!");

        }
    }
}

