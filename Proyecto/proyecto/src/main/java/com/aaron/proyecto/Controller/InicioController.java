package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface InicioController {
    public ModelAndView getUserLoged(HttpServletRequest request);
   
}
