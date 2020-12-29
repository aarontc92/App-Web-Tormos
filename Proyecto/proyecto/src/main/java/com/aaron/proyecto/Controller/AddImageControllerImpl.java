package com.aaron.proyecto.Controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.CabeceraService;
import com.aaron.proyecto.Service.ImagenesService;
import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.Service.UserService;
import com.aaron.proyecto.models.Imagenes;
import com.aaron.proyecto.models.Users;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AddImageControllerImpl implements AddImageController {

    @Autowired
    UserService userService;
    @Autowired
    CabeceraService cabeceraService;
    @Autowired
    ImagenesService imagenesService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;

    Users user;
    Imagenes imagen;
    HttpSession sesion;

    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping(value = "/addImagen", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView getFormImage(HttpServletRequest request) {
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        modelAndView.addObject("name", user.getNomUser());
        modelAndView.setViewName("htmls/addImage");

        return modelAndView;

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView processForm(MultipartFile[] file,HttpServletRequest request, String descripcion, Model model) {
        sesion = request.getSession();
       
        user = (Users) sesion.getAttribute("usuario");
        imagenesService.addImagen(user.getNomUser(), file, descripcion);
        modelAndView.setViewName("htmls/inicio");

        return modelAndView;
    }
}
