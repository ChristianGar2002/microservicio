package com.usuario.repository;

import com.usuario.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryUsuario extends JpaRepository<Usuarios, Integer> {

    Usuarios findByUsername(String username);
    Usuarios findByJwt(String jwt);
    List<Usuarios> findAllByRol(String rol);
}
