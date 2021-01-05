package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.Service.UserService;
import com.aaron.proyecto.models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class SignUpControllerImpl implements SignUpController{

    @Autowired
    UserService userService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;
    @Autowired
    InicioController userController;
    Users user;
    HttpSession sesion;
    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping(value = "/sign-up", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView registro(Model model) {
        model.addAttribute("visible", "hidden;");
        modelAndView.setViewName("htmls/sign-up");
        return modelAndView;
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView registroForm(HttpServletRequest request, Model model, String email, String nombre,
            String password, String passwordConfirm) {
        Users modificado = new Users();
        
        Boolean usuario = true;
        Boolean correo = true;
        Boolean contraseña = true;

        correo = userService.comprobarEmail(email);
        usuario = userService.comprobarUsuario(email, nombre, password, passwordConfirm);
        contraseña = userService.pass(password, passwordConfirm);
        if (!correo) {
            model.addAttribute("error", "Email ya registrado.");
            model.addAttribute("visible", "visible;");
            modelAndView.setViewName("htmls/sign-up");
        }
        if (!usuario) {
            model.addAttribute("error", "Debes rellenar todos los campos.");
            model.addAttribute("visible", "visible;");
            modelAndView.setViewName("htmls/sign-up");
        }
        if (!contraseña) {
            model.addAttribute("error", "Las contraseñas deben ser de 6 o más caracteres y deben coincidir.");
            model.addAttribute("visible", "visible;");
            modelAndView.setViewName("htmls/sign-up");

        }
        if (usuario && correo && contraseña) {
            modificado.setCorreo(email);
            modificado.setNomUser(nombre);
            modificado.setPass(password);
            modificado.setIdUsuario(userService.findAllUsers().size() + 1);

            userService.updateUser(modificado);

            modelAndView.clear();
            modelAndView.addObject("user", modificado);

            sesion = request.getSession(true);
            sesion.setAttribute("usuario", modificado);

            model.addAttribute("name", modificado.getNomUser());
            modelAndView.setViewName("htmls/perfilUser");
            return new ModelAndView("redirect:/usuario="+nombre);
        }
        return modelAndView;
    }
}
