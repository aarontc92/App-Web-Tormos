package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.Service.UserService;
import com.aaron.proyecto.models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
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

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView sendForm(Model model) {

        model.addAttribute("visible", "hidden;");

        modelAndView.setViewName("htmls/login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView processForm(HttpServletRequest request,
            @NonNull @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password, Model model) {

        
        Boolean correcto = true;
        Boolean correo = true;
        Boolean contraseña = true;

        modelAndView.setViewName("htmls/login");
        
        if (password.equals("")) {
            model.addAttribute("error", "La contraseña no debe estar vacia.");

            correcto = false;
        } else if (email.equals("")) {
            model.addAttribute("error", "El email no debe estar vacio.");
            correcto = false;
        } else {
            correo = userService.comprobarEmail(email);
            if (correo) {
                contraseña = userService.comprobarPass(password, email);
            }
        }
        if (!correo) {
            model.addAttribute("error", "Email no registrado.");
        }
        if (!contraseña) {
            model.addAttribute("error", "Contraseña incorrecta.");
        }
        modelAndView.setViewName("htmls/login");
        if (correcto && correo && contraseña) {
            sesion = request.getSession(true);
            user = userService.loadUserByEmail(email);
            sesion.setAttribute("usuario", user);
            model.addAttribute("name", user.getNomUser());
            modelAndView.setViewName("htmls/inicio");

            return new ModelAndView("redirect:/usuario=" + user.getNomUser());

        }
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public RedirectView logout(HttpServletRequest request, Model model) {
        RedirectView redir=new RedirectView();
        sesion = request.getSession();
        sesion.invalidate();
        redir = new RedirectView();
        redir.setUrl("/login");
        modelAndView.setViewName("login");
        return redir;
    }

    

}
