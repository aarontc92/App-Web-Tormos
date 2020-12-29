package com.aaron.proyecto.Controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public interface LoginController {
    public String sendForm(String username, String password);
    public String logout (HttpServletRequest request, Model model);
    public String processForm (HttpServletRequest request,String username, String password, Model model);
    public ModelAndView perfil(HttpServletRequest request, Model model);
    public ModelAndView perfilForm (HttpServletRequest request, Model model,String pass,String passConfirmed,String nom, String correo);
    public String deletePerfil(HttpServletRequest request, Model model);
}
