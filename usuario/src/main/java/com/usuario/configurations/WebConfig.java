package com.usuario.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Le indica a spring boot las configuraciones que debe de seguir
@Configuration
public class WebConfig implements WebMvcConfigurer {//Con esta clase redirigo las imagenes descargadas a la carpeta images

    //Con este metodo hacemos valido la ruta de la carpeta images para que en cualquier parte del programa podamoa poner la ruta /images/ y que el programa sepa que que esa es la carpeta que esta a haya
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {//addResourceHandlers: Este método se usa para agregar manejadores de recursos
        registry.addResourceHandler("/images/**")//cualquier URL que comience con /images/ será manejada por este recurso.
                .addResourceLocations("file:./images/");//Este método define la ubicación física desde donde se servirán los archivos. file:./images/ indica que los archivos se encuentran en un directorio llamado images en la raíz del proyecto (al mismo nivel que la carpeta src).
    }
}
