package com.materia.controllers;

import com.materia.entities.Materias;
import com.materia.services.ServiceMaterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriasController {

    @Autowired
    private ServiceMaterias serviceMaterias;

    @GetMapping
    public List<Materias> getMaterias(){

        return serviceMaterias.getMaterias();
    }

    @GetMapping("/obtener_materia/{id_materia}")
    public Materias ObtenerMaterias(@PathVariable("id_materia") int id_materia){

        return serviceMaterias.obtenerMateria(id_materia);

    }

    @PostMapping("/registrar_materia")
    public Materias registrarMateria(@RequestBody Materias materia){

        return serviceMaterias.registrarMateria(materia);
    }

    @PutMapping("/actualizar_materia/{id_materia}")
    public Materias actualizarMateria(@PathVariable("id_materia") int id_materia, @RequestBody Materias materia){

        return serviceMaterias.actualizarMeteria(materia, id_materia);

    }

    @DeleteMapping("/eliminar_materia/{id_materia}")
    public ResponseEntity<String> eliminarMateria(@PathVariable("id_materia") int id_materia){

        serviceMaterias.eliminarMateria(id_materia);

        return ResponseEntity.ok("Eliminacion completada");
    }

    @GetMapping("/materias_profesores/{id_profesor}")
    public List<Materias> ObtenerMateriasProfesores(@PathVariable("id_profesor") int id_profesor){

        return serviceMaterias.listar_profesores_materias(id_profesor);
    }
}
