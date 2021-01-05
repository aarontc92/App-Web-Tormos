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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class PerfilUserControllerImpl implements PerfilUserController {

    @Autowired
    UserService userService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;
    @Autowired
    InicioController userController;
    Users user;
    HttpSession sesion;
    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping(value = "/perfil", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView perfil(HttpServletRequest request, Model model) {
       
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        if (user != null) {
            modelAndView.addObject("user", user);
            modelAndView.addObject("imagen", userService.loadCabeceraByUsername(user.getIdUsuario()));
            model.addAttribute("visible", "hidden;");
            modelAndView.setViewName("htmls/perfilUser");
        } else {
            return new ModelAndView("redirect:/login");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/perfil", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView perfilForm(HttpServletRequest request, Model model, String correo, String pass,
            String passConfirmed, String nom, MultipartFile cabecera) {

        Users modificado = new Users();
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");

        Boolean usuario = true;
        Boolean email = true;
        Boolean contraseña = true;

        email = userService.comprobarEmail(correo);
        usuario = userService.comprobarUsuario(correo, nom, pass, passConfirmed);
        contraseña = userService.pass(pass, passConfirmed);

        if (!usuario) {
            model.addAttribute("error", "Debes rellenar todos los campos.");
            model.addAttribute("visible", "visible;");

        } else {
            if (!email) {
                model.addAttribute("error", "Email ya registrado.");
                model.addAttribute("visible", "visible;");

            } else if (!contraseña) {
                model.addAttribute("error", "Las contraseñas deben ser de 6 o más caracteres y deben coincidir.");
                model.addAttribute("visible", "visible;");
            }
        }

        modelAndView.setViewName("htmls/perfilUser");
        if (email && usuario && contraseña) {

            modificado.setCorreo(correo);
            modificado.setIdUsuario(user.getIdUsuario());
            modificado.setPass(pass);
            modificado.setNomUser(nom);
            modificado.setImagen(user.getImagen());
            userService.updateUser(modificado);

            sesion.setAttribute("usuario", modificado);
            modelAndView.addObject("user", modificado);
            model.addAttribute("visible", "hidden;");
            modelAndView.setViewName("htmls/perfilUser");
            return new ModelAndView("redirect:/perfil");

        }

        return modelAndView;
    }

    @RequestMapping(value = "/perfilImagen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView perfilImageForm(HttpServletRequest request, Model model, MultipartFile cabecera) {

        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");

        if (this.comprobacionImagen(cabecera)) {
            userService.modificarUser(user.getCorreo(), user.getPass(), user.getNomUser(), cabecera,
                    user.getIdUsuario());
            modelAndView.clear();
            modelAndView.addObject("user", user);
            model.addAttribute("visible", "hidden;");
            return new ModelAndView("redirect:/perfil");
        } else {
            model.addAttribute("error", "Debes seleccionar una imagen valida.");
            model.addAttribute("visible", "visible;");
        }

        modelAndView.setViewName("htmls/perfilUser");
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView deletePerfil(HttpServletRequest request, Model model) {

        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");

        userService.deleteUsers(user.getIdUsuario());
        sesion.invalidate();

        modelAndView.clear();
        modelAndView.setViewName("htmls/login");

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/deleteCab", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView deleteCab(HttpServletRequest request, Model model) {

        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        user.setImagen(null);

        userService.updateUser(user);

        modelAndView.clear();
        modelAndView.addObject("user", user);
        model.addAttribute("visible", "hidden;");
        modelAndView.setViewName("htmls/perfilUser");

        return new ModelAndView("redirect:/perfil");
    }

    private boolean comprobacionImagen(MultipartFile file) {

        if (file.isEmpty()) {
            System.out.println("aqui");
            return false;
        }
        return true;
    }

}
