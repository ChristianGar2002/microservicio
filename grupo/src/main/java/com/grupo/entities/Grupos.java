package com.grupo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grupos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre_grupo;
    private int numero_estudiantes;
    private int usuarioid;
}
