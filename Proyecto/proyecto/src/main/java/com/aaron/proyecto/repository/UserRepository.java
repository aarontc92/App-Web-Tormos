package com.aaron.proyecto.repository;

import java.util.Optional;

import com.aaron.proyecto.models.Users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<Users,Long>{
    public Optional<Users> findByNomUser(String username);
    public Optional<Users> findByIdUsuario(Integer idUsuario);
    public Optional<Users> findByCorreo(String correo);
}
