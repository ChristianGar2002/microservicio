package com.materia.services;

import com.materia.entities.Materias;

import java.util.List;

public interface ServiceMaterias {

    List<Materias> getMaterias();

    Materias obtenerMateria(int id);

    Materias registrarMateria(Materias materia);

    Materias actualizarMeteria(Materias materia, int id);

    void eliminarMateria(int id);

    List<Materias> listar_profesores_materias(int id_profesor);
}
