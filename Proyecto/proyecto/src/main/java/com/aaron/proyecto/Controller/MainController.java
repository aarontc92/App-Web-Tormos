package com.aaron.proyecto.Controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface MainController {
    public ModelAndView getAllImages(HttpServletRequest request);
   
}
