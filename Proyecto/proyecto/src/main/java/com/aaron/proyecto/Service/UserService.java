package com.aaron.proyecto.Service;

import java.util.List;

import com.aaron.proyecto.models.Users;


public interface UserService {
    public Users loadUserByUsername(String username);
    public void updateUser( Users usuario);
    public List<Users> findAllUsers();
    public void deleteUsers(long id);
	
}
