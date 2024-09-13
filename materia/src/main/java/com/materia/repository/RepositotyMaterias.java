package com.materia.repository;

import com.materia.entities.Materias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositotyMaterias extends JpaRepository<Materias, Integer> {

    List<Materias> findAllByUsuarioid(int usuarioid);
}
