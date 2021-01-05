package com.aaron.proyecto.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aaron.proyecto.models.Users;
import com.aaron.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private TemplateEngineConfig templateEngineConfig;
    Users user;
    List<Users> lista;
    String resultado = "";

    @Override
    public Users loadUserById(Integer idUser) {
        return userRepository.findByIdUsuario(idUser).get();
    }

    @Override
    public Users loadUserByEmail(String email) {
        return userRepository.findByCorreo(email).get();
    }

    @Override
    public Users loadUserByName(String nameUser) {
        return userRepository.findByNomUser(nameUser).get();
    }

    @Override
    public void updateUser(Users usuario) {
       
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

    @Override
    public String loadCabeceraByUsername(Integer id_user) {
        String resultado = "";
        try {
            Users imagen = userRepository.findByIdUsuario(id_user).get();
            resultado = imagen.getCabecera();
            if (resultado == null|| resultado.isEmpty()) {
                resultado = this.getImgUsuario();
            }
        } catch (Exception e) {
            resultado = this.getImgUsuario();
        }

        return resultado;
    }

    @Override
    public void modificarUser(String correo, String pass, String nom, MultipartFile file, Integer idUser) {

        user = new Users();
        user.setIdUsuario(idUser);
        user.setNomUser(nom);
        user.setCorreo(correo);
        user.setPass(pass);
        user.setCabecera(this.getBlobImage(file));

        userRepository.save(user);
    }

    @Override
    public boolean comprobarUsuario(String email, String nombre, String password, String passwordConfirm) {
        boolean correcto = true;
        if (email.equals("")) {
            correcto = false;
        }
        if (nombre.equals("")) {
            correcto = false;
        }
        if (password.equals("")) {
            correcto = false;
        }
        return correcto;
    }

    @Override
    public boolean comprobarEmail(String email) {
        
        try{
            Users usuarios=new Users();
            usuarios = userRepository.findByCorreo(email).get();
            System.out.println(usuarios.getNomUser());
        }catch(Exception e){
            return false;
        }
        
        return true;
    }

    @Override
    public boolean pass(String pass, String passConfirm) {
        boolean passw = true;
        if (pass.length() < 6) {
            passw = false;
        } else if (!pass.equals(passConfirm)) {
            passw = false;
        }
        return passw;
    }

    @Override
    public boolean comprobarPass(String pass, String email) {
        Users usuarios;
        usuarios = new Users();

        usuarios = userRepository.findByCorreo(email).get();

        if (usuarios.getPass().equals(pass)) {
            return true;
        }

        return false;
    }

    private String getImgUsuario() {
        Context context;
        String res = "";
        context = new Context();
        res = templateEngineConfig.templateEngine().process("../../static/img/user.txt", context);
        return res;
    }

    private byte[] getBlobImage(MultipartFile file) {
        byte[] bytes;

        try {

            bytes = file.getBytes();
            return bytes;

        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

}
