package com.usuario.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupos {

    private int id;
    private String nombre_grupo;
    private int numero_estudiantes;
    private int usuarioid;
}
