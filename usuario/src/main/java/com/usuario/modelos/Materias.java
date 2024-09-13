package com.usuario.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Materias {

    private int id;
    private String nombre_materia;
    private int usuarioid;
}
