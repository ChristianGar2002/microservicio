package com.usuario.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuario.controllers.utils.JWTUtils;
import com.usuario.entities.Usuarios;
import com.usuario.modelos.Grupos;
import com.usuario.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioWeb {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private JWTUtils jwtUtils;

    @GetMapping("/login")
    public String iniciar_sesion(Model model){

        model.addAttribute("usuario", new Usuarios());

        return "login";
    }

    @PostMapping("/login")
    public String iniciar_sesion_usuario(@ModelAttribute Usuarios usuario){

        Usuarios usuario_encontrado = usuarioService.iniciar_sesion_usuario(usuario);

        if(usuario_encontrado == null){

            return null;
        }

        httpSession.setAttribute("username", usuario_encontrado.getUsername());
        httpSession.setAttribute("email", usuario_encontrado.getEmail());
        httpSession.setAttribute("id", usuario_encontrado.getId());
        httpSession.setAttribute("rol", usuario_encontrado.getRol());

        return "redirect:/usuario/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        model.addAttribute("username", httpSession.getAttribute("username"));

        Usuarios usuario_sesion = usuarioService.getUsuario((int) httpSession.getAttribute("id"));

        model.addAttribute("token", usuario_sesion.getJwt());

        model.addAttribute("rol", httpSession.getAttribute("rol"));

        return "dashboard";
    }

    @GetMapping("/token")
    public String crear_token(){

        //Obtenemos el token de la sesion
        String token_JWT = jwtUtils.create(String.valueOf((int) httpSession.getAttribute("id")), (String) httpSession.getAttribute("email"));

        usuarioService.updateToken(token_JWT, (int) httpSession.getAttribute("id"));

        return "redirect:/usuario/dashboard";
    }

    //profesores
    @GetMapping("/profesores")
    public String profesores(Model model){

        model.addAttribute("username", httpSession.getAttribute("username"));

        model.addAttribute("lista_profesores", usuarioService.buscar_profesores("Profesor"));

        return "profesores";
    }

    @GetMapping("/grupos")
    public String grupos(Model model){

        model.addAttribute("username", httpSession.getAttribute("username"));

        List<Grupos> lista_grupos = usuarioService.obtenerGruposTodos();

        model.addAttribute("lista_grupos", lista_grupos);

        return "dashboard_grupos";
    }

    @GetMapping("/horarios")
    public String horarios(Model model){

        model.addAttribute("username", httpSession.getAttribute("username"));

        model.addAttribute("lista_horarios", usuarioService.obtenerHorariosTodos());

        return "dashboard_horarios";
    }

    @GetMapping("/materias")
    public String materias(Model model) {

        model.addAttribute("username", httpSession.getAttribute("username"));

        model.addAttribute("lista_materias", usuarioService.obtenerMateriasTodas());

        return "dashboard_materias";
    }

    @GetMapping("/imagen/{id}")
    public String ponerImagen(Model model, @PathVariable("id") int id_profesor) throws Exception {

        Usuarios usuario_encontrado = usuarioService.getUsuario(id_profesor);

        String url_imagen = obtenerUrlImagen(usuario_encontrado.getNombre_completo()).asText();

        byte [] imagen_byte = obtener_imagen_byte(url_imagen);

        modificar_imagen(usuario_encontrado, imagen_byte);

        return "redirect:/usuario/profesores";
    }

    private JsonNode obtenerUrlImagen(String query) throws Exception{

        //Las llaves de la api
        String API_KEY = "API KEY";
        String CX = "CX";

        //Creo este objeto de tipo cliente para hacer peticiones
        OkHttpClient client = new OkHttpClient();

        //Armo la peticion
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://www.googleapis.com/customsearch/v1").newBuilder();
        urlBuilder.addQueryParameter("key", API_KEY);
        urlBuilder.addQueryParameter("cx", CX);
        urlBuilder.addQueryParameter("q", query);
        urlBuilder.addQueryParameter("searchType", "image");
        urlBuilder.addQueryParameter("num", "1");

        //Convierto a string la peticion
        String url = urlBuilder.build().toString();

        //Creo la peticion con el string
        Request request = new Request.Builder()
                .url(url)
                .build();

        //Ejecuto la peticion y obtengo la respuesta
        Response response = client.newCall(request).execute();

        //Obtengo el resultado y lo convierto a string
        String responseBody = response.body().string();

        //Con el objeto mapper paso de objetos java a json y viceversa
        ObjectMapper mapper = new ObjectMapper();

        //Lo vuelvo json al satring de la respuesta
        JsonNode actualObj = mapper.readTree(responseBody);

        //Verifico si existe la clave items en el json
        if (actualObj.has("items")) {

            //Devuelvo un objeto de tipo jsonNode
            return actualObj.get("items").get(0).get("link");//Para que me lo de como string
        } else {
            System.out.println("No se encontraron imágenes.");
            return null;
        }
    }

    private byte[] obtener_imagen_byte(String url_imagen) throws Exception {
        OkHttpClient client = new OkHttpClient();//Para hacer peticion
        Request request = new Request.Builder().url(url_imagen).build();//creo la peticion con la url
        Response response = client.newCall(request).execute();//Ejecuto la peticion y obtengo la respuesta
        if (response.isSuccessful()) {
            return response.body().bytes();//Convierto la respuesta en bytes
        } else {
            System.out.println("No se pudo descargar la imagen: " + url_imagen);
            return null;
        }
    }

    private void modificar_imagen(Usuarios usuario, byte[] imagen_bytes){
        //Colocamos la ruta en el objeto producto
        String ruta_guardar = "./images/imagen_api-" + usuario.getId() + ".jpg";
        try {
            // Crea un objeto FileOutputStream, para colocar la imagen en la ruta que diga
            FileOutputStream fos = new FileOutputStream(ruta_guardar);

            // Escribe los bytes de la imagen en el archivo
            fos.write(imagen_bytes);

            // Cierra el FileOutputStream
            fos.close();

            usuario.setFoto("/images/imagen_api-"+usuario.getId()+".jpg");//Guardo la ruta en el campo foto, esta ruta es asi para que aparezca en la tabla

            usuarioService.updateUsuario(usuario.getId(),usuario);//Actualizo el registro de dicha imagen

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}