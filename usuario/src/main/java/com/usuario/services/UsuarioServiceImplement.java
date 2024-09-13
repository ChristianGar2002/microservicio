package com.usuario.services;

import com.usuario.entities.Usuarios;
import com.usuario.feignclients.GruposFeignClients;
import com.usuario.feignclients.HorariosFeignClients;
import com.usuario.feignclients.MateriasFeignClients;
import com.usuario.modelos.Grupos;
import com.usuario.modelos.Horario;
import com.usuario.modelos.Materias;
import com.usuario.repository.RepositoryUsuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServiceImplement implements UsuarioService{

    @Autowired
    private RepositoryUsuario repositoryUsuario;

    @Autowired
    private GruposFeignClients gruposFeignClients;

    @Autowired
    private MateriasFeignClients materiasFeignClients;

    @Autowired
    private HorariosFeignClients horariosFeignClients;

    @Override
    public Usuarios save(Usuarios usuario) {

        //Creo un objeto de tipo argon 2 para cifrar contrasena
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        //Cifro la contrasena del password
        String hash = argon2.hash(1, 1024, 1, usuario.getContrasena());

        //Le agrego la contrasena ya cifrada al objeto usuario
        usuario.setContrasena(hash);

        return repositoryUsuario.save(usuario);

    }

    @Override
    public Usuarios getUsuario(int id) {

        Usuarios usuario_encontrado = repositoryUsuario.findById(id).orElse(null);

        return usuario_encontrado;
    }

    @Override
    public List<Usuarios> getUsuarios() {

        List<Usuarios> lista_usuarios = repositoryUsuario.findAll();

        if(lista_usuarios.isEmpty()){

            return null;
        }

        return lista_usuarios;
    }

    @Override
    public Usuarios updateUsuario(int id, Usuarios usuario) {

        Usuarios usuario_encontrado = repositoryUsuario.findById(id).orElse(null);

        if (usuario_encontrado == null){

            return null;
        }

        usuario_encontrado.setContrasena(usuario.getContrasena());
        usuario_encontrado.setEdad(usuario.getEdad());
        usuario_encontrado.setRol(usuario.getRol());
        usuario_encontrado.setEmail(usuario.getEmail());
        usuario_encontrado.setNombre_completo(usuario.getNombre_completo());
        usuario_encontrado.setFoto(usuario.getFoto());

        repositoryUsuario.save(usuario_encontrado);

        return usuario_encontrado;
    }

    @Override
    public void deleteUsuario(int id) {

        repositoryUsuario.deleteById(id);

    }

    @Override
    public Usuarios iniciar_sesion_usuario(Usuarios usuario) {

        //Creo un objeto de tipo argon 2 para cifrar contrasena
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        Usuarios usuario_encontrado = repositoryUsuario.findByUsername(usuario.getUsername());

        //Verifico que el usuario encontrado no se ha nullo y ademas verifico que la contrasena se ha la misma, (Sigue el orden del hash)
        if((usuario_encontrado != null) && (argon2.verify(usuario_encontrado.getContrasena(), usuario.getContrasena()))){

            return usuario_encontrado;
        }

        return null;

    }

    @Override
    public void updateToken(String token, int id_usuario) {

        Usuarios usuario_encontrado = repositoryUsuario.findById(id_usuario).orElse(null);

        usuario_encontrado.setJwt(token);

        repositoryUsuario.save(usuario_encontrado);
    }

    @Override
    public Usuarios validar_jwt(String jwt) {

        Usuarios usuario_encontrado = repositoryUsuario.findByJwt(jwt);

        if(usuario_encontrado == null){

            return null;
        }

        return usuario_encontrado;
    }

    @Override
    public List<Usuarios> buscar_profesores(String rol) {

        List<Usuarios> profesores = repositoryUsuario.findAllByRol(rol);

        if(profesores.isEmpty()){

            return null;
        }

        return profesores;

    }

    @Override
    public List<Grupos> getGrupos(int id_profesor) {
        List<Grupos> lista_encontrada = gruposFeignClients.getGrupos(id_profesor);

        if(lista_encontrada.isEmpty()){

            return null;
        }

        return lista_encontrada;

    }

    @Override
    public List<Materias> materiasProfe(int id_profesor) {

        List<Materias> lista_materia = materiasFeignClients.materiasProfe(id_profesor);

        if(lista_materia.isEmpty()){

            return null;
        }

        return lista_materia;

    }

    @Override
    public List<Horario> horariosProfe(int id_profesor) {

        List<Horario> lista_horario = horariosFeignClients.horariosProfe(id_profesor);

        if(lista_horario.isEmpty()){

            return null;
        }

        return lista_horario;
    }

    @Override
    public List<Grupos> obtenerGruposTodos() {

        List<Grupos> obtenerGrupos = gruposFeignClients.obtenerGruposTodos();

        if(obtenerGrupos.isEmpty()){

            return null;
        }

        return obtenerGrupos;

    }

    @Override
    public List<Horario> obtenerHorariosTodos() {

        List<Horario> lista_horario = horariosFeignClients.horarioTodos();

        if(lista_horario.isEmpty()){

            return null;
        }

        return lista_horario;

    }

    @Override
    public List<Materias> obtenerMateriasTodas() {

        List<Materias> lista_materias = materiasFeignClients.materiasTodas();

        if(lista_materias.isEmpty()){

            return null;
        }

        return lista_materias;

    }

    @Override
    public Map<String, Object> obtenerTodos(int id_profesor) {

        Map<String, Object> objetoTodo = new HashMap<>();

        Usuarios usuario_encontrado = repositoryUsuario.findById(id_profesor).orElse(null);

        if(usuario_encontrado==null){

            objetoTodo.put("mensaje", "No existe el usuario");

            return objetoTodo;
        }

        usuario_encontrado.setContrasena("");

        objetoTodo.put("Usuario", usuario_encontrado);

        List<Grupos> grupo_encontrado = gruposFeignClients.getGrupos(id_profesor);

        if(grupo_encontrado.isEmpty()){

            objetoTodo.put("Grupo", "No tiene grupos asignados");
        }else{

            objetoTodo.put("Grupo", grupo_encontrado);
        }

        List<Materias> materias_encontradas = materiasFeignClients.materiasProfe(id_profesor);

        if(materias_encontradas.isEmpty()){

            objetoTodo.put("Materias", "No tiene materias asignadas");
        }else{

            objetoTodo.put("Materias", materias_encontradas);
        }

        List<Horario> horarios_encontrados = horariosFeignClients.horariosProfe(id_profesor);

        if(horarios_encontrados.isEmpty()){

            objetoTodo.put("Horarios", "No tiene horarios todavia");
        }else{

            objetoTodo.put("Horarios", horarios_encontrados);
        }

        return objetoTodo;
    }

    @Override
    public Materias gurdarMateria(Materias materia) {

        Materias materia_guardada = materiasFeignClients.guardarMateria(materia);

        if(materia_guardada==null){

            return null;
        }

        return materia_guardada;

    }
}
