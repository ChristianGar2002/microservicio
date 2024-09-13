package com.usuario.feignclients;

import com.usuario.modelos.Materias;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//Con esta anotacion indico que este sera un cliente feign, el nombre del microservicio, la url de ese microservicio, de aqui se podra acceder a los que son los metodos get y post
@FeignClient(name = "materia")
public interface MateriasFeignClients {

    @GetMapping("/materias/materias_profesores/{id_profesor}")
    public List<Materias> materiasProfe(@PathVariable("id_profesor") int id_profesor);

    @GetMapping("/materias")
    public List<Materias> materiasTodas();

    @PostMapping("/materias/registrar_materia")
    public Materias guardarMateria(@RequestBody Materias materia);
}
