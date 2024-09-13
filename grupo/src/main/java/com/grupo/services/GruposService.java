package com.grupo.services;

import com.grupo.entities.Grupos;

import java.util.List;

public interface GruposService {

    Grupos save(Grupos grupo);

    Grupos getGrupo(int id);

    List<Grupos> getGrupos();

    Grupos updateGrupo(int id, Grupos grupo);

    void deleteGrupo(int id);

    List<Grupos> lista_grupos_profesor(int usuarioid);
}
