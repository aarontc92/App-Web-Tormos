package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface PerfilUserController {
    public ModelAndView perfil(HttpServletRequest request, Model model);
    public ModelAndView perfilForm (HttpServletRequest request, Model model,String correo,String pass,String passConfirmed,String nom,MultipartFile file);
    public ModelAndView perfilImageForm(HttpServletRequest request, Model model, MultipartFile cabecera);
    public ModelAndView deletePerfil(HttpServletRequest request, Model model);
    public ModelAndView deleteCab(HttpServletRequest request, Model model);
}
