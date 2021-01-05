package com.aaron.proyecto.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        if(user!=null){
             modelAndView.addObject("name", user.getNomUser());
        modelAndView.addObject("visibleError", "hidden;");
        modelAndView.addObject("visibleSucces", "hidden;");
        modelAndView.setViewName("htmls/addImage");
        }else{
            return new ModelAndView("redirect:/login");
        }
        return modelAndView;

    }

    @RequestMapping(value = "/addImagen", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView processForm(MultipartFile[] file, HttpServletRequest request, String descripcion, String titulo,
            Model model) {
        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");
        boolean comprueba = this.comprobacionImagen(file);
        if (comprueba) {
            try {
                imagenesService.addImagen(user.getIdUsuario(), file, descripcion, titulo);
                modelAndView.addObject("succes", "Imagen subida con exito.");
                modelAndView.addObject("visibleSucces", "visible;");
            } catch (Exception e) {
                modelAndView.addObject("error", "Formato de archivo incorrecto o demasiado pesado.");
                modelAndView.addObject("visibleError", "visible;");
            }
        } else {
            modelAndView.addObject("error", "Debes selecionar una imagen valida.");
            modelAndView.addObject("visibleError", "visible;");
        }

        modelAndView.setViewName("htmls/addImage");

        return modelAndView;
    }

    private boolean comprobacionImagen(MultipartFile[] file) {
        for (MultipartFile multipartFile : file) {
            if (multipartFile.isEmpty()) {
            
                return  false;
            }
        }
        return true;
    }
}
