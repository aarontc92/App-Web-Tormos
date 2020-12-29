package com.aaron.proyecto.Service;

import java.util.ArrayList;
import java.util.List;

import com.aaron.proyecto.models.Users;
import com.aaron.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    Users user;
    List<Users> lista;

    @Override
    public Users loadUserByUsername(String username) {
        return userRepository.findByNomUser(username).get();
    }
    @Override
    public void updateUser( Users usuario) {
       
       userRepository.save(usuario);
    }

    @Override
    public List<Users> findAllUsers() {
        List<Users> user = new ArrayList<>();
        userRepository.findAll().forEach(user::add);
        return user;
    }
    @Override
    public void deleteUsers(long id) {
        
        userRepository.deleteById(id);
        
    }

}
