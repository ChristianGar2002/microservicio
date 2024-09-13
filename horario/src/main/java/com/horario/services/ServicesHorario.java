package com.horario.services;

import com.horario.entities.Horario;

import java.util.List;

public interface ServicesHorario {

    List<Horario> getHorarios();

    Horario obtenerHorario(int id);

    Horario registrarHorario(Horario horario);

    Horario actualizarHorario(Horario horario, int id);

    void eliminarHorario(int id);

    List<Horario> listar_profesores_horarios(int id_profesor);

}
