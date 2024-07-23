package com.horario.services;

import com.horario.entities.Horario;
import com.horario.repository.RepositoryHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesHorarioImplement implements ServicesHorario{

    @Autowired
    private RepositoryHorario repositoryHorario;

    @Override
    public List<Horario> getHorarios() {

        List<Horario> horarios_encontrados = repositoryHorario.findAll();

        if(horarios_encontrados.isEmpty()){

            return null;
        }

        return horarios_encontrados;

    }

    @Override
    public Horario obtenerHorario(int id) {

        return repositoryHorario.findById(id).orElse(null);
    }

    @Override
    public Horario registrarHorario(Horario horario) {

        Horario horario_guardado = repositoryHorario.save(horario);

        if(horario_guardado==null){

            return null;
        }

        return horario_guardado;

    }

    @Override
    public Horario actualizarHorario(Horario horario, int id) {

        Horario horario_encontrado = repositoryHorario.findById(id).orElse(null);

        if(horario_encontrado == null){

            return null;
        }

        horario_encontrado.setUsuarioid(horario.getUsuarioid());
        horario_encontrado.setMateriaid(horario.getMateriaid());
        horario_encontrado.setGrupoid(horario.getGrupoid());
        horario_encontrado.setDias_semana(horario.getDias_semana());
        horario_encontrado.setHoras(horario.getHoras());

        return repositoryHorario.save(horario_encontrado);
    }

    @Override
    public void eliminarHorario(int id) {

        repositoryHorario.deleteById(id);
    }

    @Override
    public List<Horario> listar_profesores_horarios(int id_profesor) {

        List<Horario> lista_horario = repositoryHorario.findAllByUsuarioid(id_profesor);

        if(lista_horario.isEmpty()){

            return null;
        }

        return lista_horario;

    }
}
