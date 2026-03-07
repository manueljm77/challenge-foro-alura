package com.alura.alura_foro.security;



import com.alura.alura_foro.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Obtenemos el token del encabezado de la petición
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {

            var token = authHeader.replace("Bearer ", "");

            // 2. Extraemos el nombre de usuario usando el método que acabas de hacer
            var nombreUsuario = tokenService.getSubject(token);

            if (nombreUsuario != null) {
                // 3. Buscamos al usuario en la base de datos
                var usuario = usuarioRepository.findByLogin(nombreUsuario);

                // 4. Forzamos un inicio de sesión en Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 5. Dejamos que la petición continúe su camino hacia el controlador
        filterChain.doFilter(request, response);
    }
}