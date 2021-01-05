package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface SignUpController {
    public ModelAndView registro(Model model);
    public ModelAndView registroForm(HttpServletRequest request, Model model ,String email,String nombre, String password,String passwordConfirm);
}
