package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.ImagenesService;
import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.Service.UserService;
import com.aaron.proyecto.models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class InicioControllerImpl implements InicioController {

    @Autowired
    UserService userService;
    @Autowired
    ImagenesService imagenesService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;

    Users user;
    HttpSession sesion;

    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping(value = "/usuario={username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView getUserLoged(HttpServletRequest request) {
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        String imagen = "";
        StringBuffer url = request.getRequestURL();
        String[] path = url.toString().split("/");
        String[] idString=path[3].split("=");
        String  name=idString[1];

        Integer id = userService.loadUserByName(name).getIdUsuario();
        imagen = userService.loadCabeceraByUsername(id);
        if (user != null) {
            modelAndView.addObject("hiddenUser", "visible");
            modelAndView.addObject("hiddenUserLogin", "hidden");
            modelAndView.addObject("name", user.getNomUser());
            modelAndView.addObject("imagenes", imagenesService.loadDatos(id));
            modelAndView.addObject("imagen", imagen);
        } else {
            modelAndView.clear();
            modelAndView.addObject("hiddenUser", "hidden");
            modelAndView.addObject("hiddenUserLogin", "visible");
            modelAndView.addObject("name", "");
            modelAndView.addObject("imagenes", imagenesService.loadDatos(id));
            modelAndView.addObject("imagen", imagen);
        }
        modelAndView.setViewName("htmls/inicio");

        return modelAndView;

    }

}
