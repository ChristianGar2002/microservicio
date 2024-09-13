package com.usuario.controllers;

import com.usuario.controllers.utils.JWTUtils;
import com.usuario.entities.Usuarios;
import com.usuario.modelos.Grupos;
import com.usuario.modelos.Horario;
import com.usuario.modelos.Materias;
import com.usuario.services.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping
    public ResponseEntity<List<Usuarios>> obtenerUsuarios(@RequestHeader(value = "Authorization") String token){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        List<Usuarios> lista_usuarios = usuarioService.getUsuarios();

        if(lista_usuarios.isEmpty()){

            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(lista_usuarios);
    }

    @PostMapping("/registrar_profesor")
    public ResponseEntity<Usuarios> guardarUsuario(@RequestHeader(value = "Authorization") String token, @RequestBody Usuarios usuario){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        usuario.setRol("Profesor");

        Usuarios usuario_guaradado = usuarioService.save(usuario);

        return ResponseEntity.ok(usuario_guaradado);
    }

    @GetMapping("/obtener_profesor/{id_profesor}")
    public ResponseEntity<Usuarios> obtenerProfesor(@RequestHeader(value = "Authorization") String token, @PathVariable("id_profesor") int id_profesor){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        Usuarios usuario_encontrado = usuarioService.getUsuario(id_profesor);

        if(usuario_encontrado == null){

            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario_encontrado);
    }

    @PutMapping("/actualizar_profesor/{id_profesor}")
    public ResponseEntity<Usuarios> actualizarProfesor(@RequestHeader(value = "Authorization") String token, @RequestBody Usuarios usuario, @PathVariable("id_profesor") int id_profesor){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        Usuarios usuario_actualizado = usuarioService.updateUsuario(id_profesor, usuario);

        if(usuario_actualizado == null){

            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario_actualizado);
    }

    @DeleteMapping("/eliminar_profesor/{id_profesor}")
    public ResponseEntity<String> eliminarProfesor(@RequestHeader(value = "Authorization") String token, @PathVariable("id_profesor") int id_profesor){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        usuarioService.deleteUsuario(id_profesor);

        return ResponseEntity.ok("El usuario ha sido eliminado");
    }

    @CircuitBreaker(name = "gruposCB", fallbackMethod = "fallbackobtenerGrupos")
    @GetMapping("/lista_grupos/{id_profesor}")
    public ResponseEntity<List<Grupos>> obtenerGrupos(@RequestHeader(value = "Authorization") String token, @PathVariable("id_profesor") int id_profesor){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        return ResponseEntity.ok(usuarioService.getGrupos(id_profesor));
    }

    @CircuitBreaker(name = "materiasCB", fallbackMethod = "fallbackobtenerMaterias")
    @GetMapping("/lista_materias/{id_profesor}")
    public ResponseEntity<List<Materias>> obtenerMaterias(@RequestHeader(value = "Authorization") String token, @PathVariable("id_profesor") int id_profesor){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        return ResponseEntity.ok(usuarioService.materiasProfe(id_profesor));
    }

    @CircuitBreaker(name = "materiasCB", fallbackMethod = "fallbackguardarMaterias")
    @PostMapping("/guardar_materia")
    public ResponseEntity<Materias> guardarMateria(@RequestHeader(value = "Authorization") String token, @RequestBody Materias materia){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        return ResponseEntity.ok(usuarioService.gurdarMateria(materia));

    }

    @CircuitBreaker(name = "horariosCB", fallbackMethod = "fallbackobtenerHorario")
    @GetMapping("/lista_horario/{id_profesor}")
    public ResponseEntity<List<Horario>> obtenerHorario(@RequestHeader(value = "Authorization") String token, @PathVariable("id_profesor") int id_profesor){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        return ResponseEntity.ok(usuarioService.horariosProfe(id_profesor));
    }

    @CircuitBreaker(name = "todaCB", fallbackMethod = "fallbackobtenerTodos")
    @GetMapping("/toda_informacion/{id_profesor}")
    public ResponseEntity<Map<String, Object>> obtenerTodos(@RequestHeader(value = "Authorization") String token,@PathVariable("id_profesor") int id_profesor){

        //Verfico la validez y actualidad el jwt
        if(usuarioService.validar_jwt(token) == null){//Si no es valido

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

        Map<String, Object> todos = new HashMap<>();

        todos = usuarioService.obtenerTodos(id_profesor);

        return ResponseEntity.ok(todos);

    }

    //Metodo para devolver la url de la foto
    @GetMapping("/imagenes/{imageName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageName) throws IOException {
        // Supongamos que las imágenes están en la carpeta /static/images
        File imgFile = new File("./images/" + imageName);//Se usa file para acceder a la carpeta images que esta fuera del clashpath de la aplicacion y que no son estaticos
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Ajusta el tipo de contenido según tus imágenes
                .body(new InputStreamResource(new FileInputStream(imgFile)));//Es un flujo de entrada para que se pueda obtener los bytes de la imagen que esta en la ruta de images
    }

    private ResponseEntity<List<Grupos>> fallbackobtenerGrupos(@PathVariable("id_profesor") int id_profesor, RuntimeException exception){//Si no se puede acceder al microservicio carro sucedaedara esto

        return new ResponseEntity("Los grupos del profesor: " + id_profesor + " no pueden ser mostrados en este momento", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<List<Materias>> fallbackobtenerMaterias(@RequestHeader(value = "Authorization") String token, @PathVariable("id_profesor") int id_profesor, RuntimeException exception){//Si no se puede acceder al microservicio carro sucedaedara esto

        return new ResponseEntity("La materia del profesor: " + id_profesor + " no pueden ser mostradas en este momento", HttpStatus.INTERNAL_SERVER_ERROR );
    }

    private ResponseEntity<List<Materias>> fallbackguardarMaterias(@RequestHeader(value = "Authorization") String token, @RequestBody Materias materia, RuntimeException exception){//Si no se puede acceder al microservicio carro sucedaedara esto

        return new ResponseEntity("La materia no se puede guardar en este momento", HttpStatus.INTERNAL_SERVER_ERROR );
    }

    private ResponseEntity<List<Horario>> fallbackobtenerHorario(@PathVariable("id_profesor") int id_profesor, RuntimeException exception){//Si no se puede acceder al microservicio carro sucedaedara esto

        return new ResponseEntity("Los horarios del profesor: " + id_profesor + " no pueden ser mostrados en este momento", HttpStatus.INTERNAL_SERVER_ERROR );
    }

    private ResponseEntity<Map<String, Object>> fallbackobtenerTodos(@PathVariable("id_profesor") int id_profesor, RuntimeException exception){//Si no se puede acceder al microservicio carro sucedaedara esto

        return new ResponseEntity("Los datos del profesor: " + id_profesor + " no pueden ser mostrados en este momento", HttpStatus.INTERNAL_SERVER_ERROR );
    }

    //Para validar el token
    private boolean validarToken(String token){

        String usuarioId = jwtUtils.getKey(token);//Para obtener el id del usuario

        if(usuarioId != null){

            return true;
        }else{

            return false;
        }

    }
}
