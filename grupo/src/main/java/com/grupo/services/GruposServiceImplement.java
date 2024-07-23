package com.grupo.services;

import com.grupo.entities.Grupos;
import com.grupo.repository.GruposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GruposServiceImplement implements GruposService{

    @Autowired
    private GruposRepository gruposRepository;

    @Override
    public Grupos save(Grupos grupo) {

        return gruposRepository.save(grupo);
    }

    @Override
    public Grupos getGrupo(int id) {
        Grupos grupo_encontrado = gruposRepository.findById(id).orElse(null);

        return grupo_encontrado;
    }

    @Override
    public List<Grupos> getGrupos() {
        List<Grupos> lista_grupos = gruposRepository.findAll();

        if(lista_grupos.isEmpty()){

            return null;
        }

        return lista_grupos;
    }

    @Override
    public Grupos updateGrupo(int id, Grupos grupo) {
        Grupos grupo_encontrado = gruposRepository.findById(id).orElse(null);

        if (grupo_encontrado == null){

            return null;
        }

        grupo_encontrado.setNombre_grupo(grupo.getNombre_grupo());
        grupo_encontrado.setNumero_estudiantes(grupo.getNumero_estudiantes());
        grupo_encontrado.setUsuarioid(grupo.getUsuarioid());

        gruposRepository.save(grupo_encontrado);

        return grupo_encontrado;
    }

    @Override
    public void deleteGrupo(int id) {

        gruposRepository.deleteById(id);
    }

    @Override
    public List<Grupos> lista_grupos_profesor(int id_usuario) {

        List<Grupos> lista_grupos_profesor = gruposRepository.findAllByUsuarioid(id_usuario);

        if(lista_grupos_profesor.isEmpty()){

            return null;
        }

        return lista_grupos_profesor;

    }
}
