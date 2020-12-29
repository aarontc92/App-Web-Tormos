package com.aaron.proyecto.repository;

import java.util.Optional;

import com.aaron.proyecto.models.Cabecera;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabeceraRepository extends CrudRepository<Cabecera,Long>{
    public Optional<Cabecera> findByNomCab(String nomCab);
}
