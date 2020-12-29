package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface AddImageController {
    public ModelAndView getFormImage(HttpServletRequest request);
    public ModelAndView processForm(MultipartFile[] file,HttpServletRequest request, String descripcion, Model model);
}
