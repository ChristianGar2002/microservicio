package com.usuario.feignclients;

import com.usuario.modelos.Grupos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//Con esta anotacion indico que este sera un cliente feign, el nombre del microservicio, la url de ese microservicio, de aqui se podra acceder a los que son los metodos get y post
@FeignClient(name = "grupo")
public interface GruposFeignClients {

    @GetMapping("/grupos/grupos_profesor/{id_profesor}")
    public List<Grupos> getGrupos(@PathVariable("id_profesor") int id_profesor);

    @GetMapping("/grupos")
    public List<Grupos> obtenerGruposTodos();
}
