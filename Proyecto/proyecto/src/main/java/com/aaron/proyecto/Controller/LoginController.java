package com.aaron.proyecto.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

public interface LoginController {
    public ModelAndView sendForm(Model model);
    public RedirectView logout (HttpServletRequest request, Model model);
    public ModelAndView processForm(HttpServletRequest request,@RequestParam(value = "email", required = true) String email,@RequestParam(value = "password", required = true) String password, Model model);
 
}
