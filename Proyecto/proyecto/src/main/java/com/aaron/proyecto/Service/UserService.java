package com.aaron.proyecto.Service;

import java.util.List;

import com.aaron.proyecto.models.Users;

import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    public Users loadUserById(Integer idUser);
    public void updateUser( Users usuario);
    public List<Users> findAllUsers();
    public void deleteUsers(long id);
    public Users loadUserByEmail(String email);
    public String loadCabeceraByUsername(Integer id_user);
    public Users loadUserByName(String nameUser);
    public void modificarUser(String correo, String pass, String nom,MultipartFile file,Integer idUser);
    public boolean comprobarUsuario(String email, String nombre, String password, String passwordConfirm);
    public boolean comprobarEmail(String email);
    public boolean pass(String pass, String passConfirm);
    public boolean comprobarPass(String pass, String email);
	
}
