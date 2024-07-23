package com.grupo.controllers;

import com.grupo.entities.Grupos;
import com.grupo.services.GruposService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GruposService gruposService;

    @GetMapping
    public List<Grupos> getGrupos(){

        List<Grupos> lista_grupos = gruposService.getGrupos();

        if(lista_grupos.isEmpty()){

            return null;
        }

        return lista_grupos;

    }

    @GetMapping("/obtener_grupo/{id_grupo}")
    public Grupos obtenerGrupos(@PathVariable("id_grupo") int id_grupo){

        Grupos grupo_encontrado = gruposService.getGrupo(id_grupo);

        if(grupo_encontrado == null){

            return null;
        }

        return grupo_encontrado;

    }

    @PostMapping("/registrar_grupo")
    public Grupos registrarGrupo(@RequestBody Grupos grupo){

        Grupos grupo_guadado = gruposService.save(grupo);

        if(grupo_guadado == null){

            return null;
        }

        return grupo_guadado;

    }

    @PutMapping("/editar_grupo/{id_grupo}")
    public Grupos actualizarGrupo(@RequestBody Grupos grupo, @PathVariable("id_grupo") int id_grupo){

        Grupos grupo_actualizado = gruposService.updateGrupo(id_grupo, grupo);

        if(grupo_actualizado == null){

            return null;
        }

        return grupo_actualizado;
    }

    @DeleteMapping("/eliminar_grupo/{id_grupo}")
    public String eliminarGrupo(@PathVariable("id_grupo") int id_grupo){

        gruposService.deleteGrupo(id_grupo);

        return "Elimando con exito";
    }

    @GetMapping("/grupos_profesor/{id_profesor}")
    public List<Grupos> buscar_grupos_profesor(@PathVariable("id_profesor") int id_profesor){

        List<Grupos> lista_profesor = gruposService.lista_grupos_profesor(id_profesor);

        if(lista_profesor.isEmpty()){

            return null;
        }

        return lista_profesor;

    }
}
