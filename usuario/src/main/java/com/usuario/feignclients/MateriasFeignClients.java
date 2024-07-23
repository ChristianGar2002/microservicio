package com.usuario.feignclients;

import com.usuario.modelos.Materias;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Con esta anotacion indico que este sera un cliente feign, el nombre del microservicio, la url de ese microservicio, de aqui se podra acceder a los que son los metodos get y post
@FeignClient(name = "materia", url = "http://localhost:8003/materias")
public interface MateriasFeignClients {

    @GetMapping("/materias_profesores/{id_profesor}")
    public List<Materias> materiasProfe(@PathVariable("id_profesor") int id_profesor);

    @GetMapping
    public List<Materias> materiasTodas();
}
