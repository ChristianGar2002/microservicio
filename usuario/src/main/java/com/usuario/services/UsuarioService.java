package com.usuario.services;

import com.usuario.entities.Usuarios;
import com.usuario.modelos.Grupos;
import com.usuario.modelos.Horario;
import com.usuario.modelos.Materias;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UsuarioService {

    Usuarios save(Usuarios usuarios);

    Usuarios getUsuario(int id);

    List<Usuarios> getUsuarios();

    Usuarios updateUsuario(int id, Usuarios usuario);

    void deleteUsuario(int id);

    Usuarios iniciar_sesion_usuario(Usuarios usuario);

    void updateToken(String token, int id_usuario);

    Usuarios validar_jwt(String jwt);

    List<Usuarios> buscar_profesores(String rol);

    List<Grupos> getGrupos(int id_profesor);

    List<Materias> materiasProfe(int id_profesor);

    List<Horario> horariosProfe(int id_profesor);

    List<Grupos> obtenerGruposTodos();

    List<Horario> obtenerHorariosTodos();

    List<Materias> obtenerMateriasTodas();

    Map<String, Object> obtenerTodos(int id_profesor);

    Materias gurdarMateria(Materias materia);
}
