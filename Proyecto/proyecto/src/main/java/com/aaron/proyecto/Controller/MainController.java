package com.aaron.proyecto.Controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface MainController {
    public ModelAndView getAllImages(HttpServletRequest request);
    public ModelAndView getImage(HttpServletRequest request);
    public ModelAndView deleteImagen(HttpServletRequest request, Model model);
    public ModelAndView updateImagen(HttpServletRequest request, Model model,String titulo,String descripcion);
}
