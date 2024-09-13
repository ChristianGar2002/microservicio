package com.horario.controllers;

import com.horario.entities.Horario;
import com.horario.services.ServicesHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horario")
public class HorarioController {

    @Autowired
    private ServicesHorario servicesHorario;

    @GetMapping
    public List<Horario> getHorarios(){

        return servicesHorario.getHorarios();
    }

    @GetMapping("/obtener_horario/{id_horario}")
    public Horario ObtenerHorarios(@PathVariable("id_horario") int id_horario){

        return servicesHorario.obtenerHorario(id_horario);

    }

    @PostMapping("/registrar_horario")
    public Horario registrarHorario(@RequestBody Horario horario){

        return servicesHorario.registrarHorario(horario);
    }

    @PutMapping("/actualizar_horario/{id_horario}")
    public Horario actualizarHorario(@PathVariable("id_horario") int id_horario, @RequestBody Horario horario){

        return servicesHorario.actualizarHorario(horario, id_horario);

    }

    @DeleteMapping("/eliminar_horario/{id_horario}")
    public ResponseEntity<String> eliminarHorario(@PathVariable("id_horario") int id_horario){

        servicesHorario.eliminarHorario(id_horario);

        return ResponseEntity.ok("Eliminacion completada");
    }

    @GetMapping("/horarios_profesores/{id_profesor}")
    public List<Horario> ObtenerHorariosProfesores(@PathVariable("id_profesor") int id_profesor){

        return servicesHorario.listar_profesores_horarios(id_profesor);
    }
}
