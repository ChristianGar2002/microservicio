package com.usuario.feignclients;

import com.usuario.modelos.Horario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Con esta anotacion indico que este sera un cliente feign, el nombre del microservicio, la url de ese microservicio, de aqui se podra acceder a los que son los metodos get y post
@FeignClient(name = "horario")
public interface HorariosFeignClients {

    @GetMapping("/horario/horarios_profesores/{id_profesor}")
    public List<Horario> horariosProfe(@PathVariable("id_profesor") int id_profesor);

    @GetMapping("/horario")
    public List<Horario>horarioTodos();
}
