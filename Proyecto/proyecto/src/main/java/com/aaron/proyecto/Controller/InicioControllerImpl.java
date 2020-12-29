package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.CabeceraService;
import com.aaron.proyecto.Service.ImagenesService;
import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.Service.UserService;
import com.aaron.proyecto.models.Cabecera;
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
    CabeceraService cabeceraService;
    @Autowired
    ImagenesService imagenesService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;

    Cabecera cabecera;
    Users user;
    HttpSession sesion;

    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping(value = "/name={username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView getUserLoged(HttpServletRequest request) {
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        String imagen = "";
        try {

            imagen = cabeceraService.loadCabeceraByUsername(user.getNomUser());
            modelAndView.clear();
            modelAndView.addObject("imagenes", imagenesService.loadDatos(user.getNomUser()));
            modelAndView.addObject("imagen", imagen);
        } catch (Exception e) {
            modelAndView.clear();
            modelAndView.addObject("imagenes", "");
            modelAndView.addObject("imagen", "");
        }
        modelAndView.addObject("name", user.getNomUser());
        modelAndView.setViewName("htmls/inicio");

        return modelAndView;

    }

}
