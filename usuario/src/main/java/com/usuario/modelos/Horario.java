package com.usuario.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Horario {

    private int id;
    private int usuarioid;
    private int grupoid;
    private int materiaid;
    private String dias_semana;
    private String horas;
}
