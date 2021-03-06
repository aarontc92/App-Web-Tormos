package com.aaron.proyecto.repository;
import java.util.List;
import java.util.Optional;

import com.aaron.proyecto.models.Imagenes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenesRepository extends CrudRepository<Imagenes,Long>{
    public List<Imagenes>  findByUsuario(Integer usuario);
    public Optional<Imagenes> findByIdFoto(Long idFoto);
    
}
