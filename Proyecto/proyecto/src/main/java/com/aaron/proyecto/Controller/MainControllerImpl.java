package com.aaron.proyecto.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aaron.proyecto.Service.ImagenesService;
import com.aaron.proyecto.Service.TemplateEngineConfig;
import com.aaron.proyecto.Service.UserService;
import com.aaron.proyecto.models.Imagenes;
import com.aaron.proyecto.models.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainControllerImpl implements MainController {
    @Autowired
    ImagenesService imagenesService;
    @Autowired
    UserService usersService;
    @Autowired
    TemplateEngineConfig templateEngineConfig;

    List<Imagenes> imgs;
    Users user;
    HttpSession sesion;
    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView getAllImages(HttpServletRequest request) {
        modelAndView.clear();

        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");

        imgs = imagenesService.loadAllImages();
        
        modelAndView.addObject("imagenes", imgs);

        if (user == null) {
            modelAndView.addObject("visibleLogin", "visible");
            modelAndView.addObject("visibleInicio", "hidden");
        } else {
            modelAndView.addObject("name", user.getNomUser());
            modelAndView.addObject("visibleLogin", "hidden");
            modelAndView.addObject("visibleInicio", "visible");
        }

        modelAndView.setViewName("htmls/main");
        return modelAndView;
    }

    @RequestMapping(value = "/id={idFoto}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView getImage(HttpServletRequest request) {
    
       if(request.getSession().getAttribute("usuario")==null){
            return new ModelAndView("redirect:/login");
       }else{
            Users userImagen = new Users();
            Imagenes imagen = new Imagenes();
            sesion = request.getSession();
            user = (Users) sesion.getAttribute("usuario");

            StringBuffer url = request.getRequestURL();
            String[] path=url.toString().split("/");
            String[] idString=path[3].split("=");
            Long id=Long.parseLong(idString[1]);

            imagen = imagenesService.loadImagenByIdFoto(id);
            userImagen = usersService.loadUserById(imagen.getUsuario());

            modelAndView.clear();
            modelAndView.addObject("imagenes", imagen);
            modelAndView.addObject("imagen",usersService.loadCabeceraByUsername(userImagen.getIdUsuario()));
            modelAndView.addObject("nomUser",userImagen.getNomUser() );
            if(user.getIdUsuario()==imagen.getUsuario()){
                modelAndView.addObject("visibleLog", "visible");
            }else{
                modelAndView.addObject("visibleLog", "hidden");
            }
            
            modelAndView.setViewName("htmls/imagen");
            modelAndView.addObject("name", user.getNomUser());
        }
        return modelAndView;
    }
    @RequestMapping(value = "/eliminarImagen_id={idFoto}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView deleteImagen(HttpServletRequest request, Model model) {

        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");

        StringBuffer url = request.getRequestURL();
        String[] path=url.toString().split("/");
        String[] idString=path[3].split("=");
        Long id=Long.parseLong(idString[1]);

        imagenesService.deleteImage(id);


        modelAndView.clear();
        modelAndView.setViewName("htmls/inicio");

        return new ModelAndView("redirect:/usuario="+user.getNomUser());
    }

    @RequestMapping(value = "/modificarImagen_id={idFoto}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ModelAndView updateImagen(HttpServletRequest request, Model model,String titulo,String descripcion) {

        sesion = request.getSession();
        user = (Users) sesion.getAttribute("usuario");

        StringBuffer url = request.getRequestURL();
        String[] path=url.toString().split("/");
        String[] idString=path[3].split("=");
        Long id=Long.parseLong(idString[1]);

        imagenesService.updateImage(id, user.getIdUsuario(),descripcion, titulo);

        modelAndView.clear();
        modelAndView.setViewName("htmls/imagen");

        return new ModelAndView("redirect:/id="+id);
    }

}
