package com.alura.alura_foro.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.alura.alura_foro.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.login = :login")
        UserDetails findByLogin(@Param("login") String login);

    }