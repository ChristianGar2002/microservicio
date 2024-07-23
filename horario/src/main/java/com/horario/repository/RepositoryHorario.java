package com.horario.repository;

import com.horario.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryHorario extends JpaRepository<Horario, Integer> {

    List<Horario> findAllByUsuarioid(int usuarioid);
}
