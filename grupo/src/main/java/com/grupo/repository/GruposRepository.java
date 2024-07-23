package com.grupo.repository;

import com.grupo.entities.Grupos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GruposRepository extends JpaRepository<Grupos, Integer> {

    List<Grupos> findAllByUsuarioid(int usuarioid);
}
