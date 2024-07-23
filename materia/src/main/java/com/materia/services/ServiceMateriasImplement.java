package com.materia.services;

import com.materia.entities.Materias;
import com.materia.repository.RepositotyMaterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceMateriasImplement implements ServiceMaterias{

    @Autowired
    private RepositotyMaterias repositotyMaterias;

    @Override
    public List<Materias> getMaterias() {
        return repositotyMaterias.findAll();
    }

    @Override
    public Materias obtenerMateria(int id) {

        return repositotyMaterias.findById(id).orElse(null);
    }

    @Override
    public Materias registrarMateria(Materias materia) {
        Materias materia_registrada = repositotyMaterias.save(materia);

        if(materia_registrada == null){

            return null;
        }

        return materia_registrada;
    }

    @Override
    public Materias actualizarMeteria(Materias materia, int id) {

        Materias materia_encontrada = repositotyMaterias.findById(id).orElse(null);

        if(materia_encontrada == null){

            return null;
        }

        materia_encontrada.setNombre_materia(materia.getNombre_materia());
        materia_encontrada.setUsuarioid(materia.getUsuarioid());

        return repositotyMaterias.save(materia_encontrada);
    }

    @Override
    public void eliminarMateria(int id) {

        repositotyMaterias.deleteById(id);
    }

    @Override
    public List<Materias> listar_profesores_materias(int id_profesor) {

        List<Materias> lista_materias_profe = repositotyMaterias.findAllByUsuarioid(id_profesor);

        if(lista_materias_profe.isEmpty()){

            return null;
        }

        return lista_materias_profe;


    }
}
