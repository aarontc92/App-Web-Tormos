package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.ImagenesService;
import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainControllerImpl implements MainController {
    @Autowired
    ImagenesService imagenesService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;

    Users user;
    HttpSession sesion;
    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView getAllImages(HttpServletRequest request) {
        modelAndView.clear();
        modelAndView.addObject("imagenes", imagenesService.loadAllImages());
        sesion = request.getSession();
        user=(Users)sesion.getAttribute("usuario");
        if(user==null){
            modelAndView.addObject("name", "Invitado");
            modelAndView.addObject("visibleLogin","visible");
            modelAndView.addObject("visibleInicio","hidden");
        }else{
            modelAndView.addObject("name", user.getNomUser());
            modelAndView.addObject("visibleLogin","hidden");
            modelAndView.addObject("visibleInicio","visible");
        }
     
        modelAndView.setViewName("htmls/main");
        return modelAndView;
    }
    
}
