package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.Service.UserService;
import com.aaron.proyecto.models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginImpl implements LoginController {

    @Autowired
    UserService userService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;
    @Autowired
    InicioController userController;
    Users user;
    HttpSession sesion;
    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/login")
    public String sendForm(String username, String password) {
        return "htmls/login";
    }

    @PostMapping("/login")
    public String processForm(HttpServletRequest request, String username, String password, Model model) {
        try {
            user = userService.loadUserByUsername(username);
            if (password.equals(user.getPass())) {
                sesion = request.getSession(true);
                sesion.setAttribute("usuario", user);
                model.addAttribute("name", username);
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
            model.addAttribute("name", "invitado");
        }
        return "redirect:/name=" + username;
    }

    @PostMapping("/")
    @Override
    public String logout(HttpServletRequest request, Model model) {
        sesion = request.getSession();
        sesion.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(HttpServletRequest request, Model model) {
        
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        modelAndView.clear();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("htmls/perfilUser");
        return modelAndView;
    }

    @PostMapping("/perfil")
    @Override
    public ModelAndView perfilForm(HttpServletRequest request, Model model, String pass, String passConfirmed,
            String nom, String correo) {
        Users modificado = new Users();
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        if (pass.equals(passConfirmed)) {
            modificado.setCorreo(correo);
            modificado.setIdUsuario(user.getIdUsuario());
            modificado.setNomUser(nom);
            modificado.setPass(pass);
            modificado.toString();
            userService.updateUser(modificado);
            modelAndView.clear();
            modelAndView.addObject("user", modificado);
            modelAndView.setViewName("htmls/perfilUser");
        }

        return modelAndView;
    }

    @PostMapping("/home")
    @Override
    public String deletePerfil(HttpServletRequest request, Model model) {

        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");

        userService.deleteUsers(user.getIdUsuario());
        sesion.invalidate();
       
        modelAndView.clear();
        
        return "redirect:/login";
    }

    
}
