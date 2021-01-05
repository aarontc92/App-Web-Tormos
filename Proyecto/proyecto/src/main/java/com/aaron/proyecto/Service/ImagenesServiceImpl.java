package com.aaron.proyecto.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.aaron.proyecto.models.Imagenes;
import com.aaron.proyecto.repository.ImagenesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

@Service
public class ImagenesServiceImpl implements ImagenesService {
    @Autowired
    ImagenesRepository imagenesRepository;
    @Autowired
    private TemplateEngineConfig templateEngineConfig;
    Imagenes imagen;
    List<Imagenes> imagenes;
    List<String> datoImagen;
    String resultado = "";

    @Override
    public List<String> loadImagesByUsername(Integer username) {
        datoImagen = new ArrayList<String>();
        imagenes = new ArrayList<Imagenes>();
        imagenesRepository.findByUsuario(username).forEach(imagenes::add);

        for (Imagenes foto : imagenes) {
            try {

                resultado = foto.getImagen();
                datoImagen.add(resultado);
            } catch (Exception e) {
                datoImagen.add(this.getImg());
            }
        }
        return datoImagen;
    }

    @Override
    public List<Imagenes> loadDatos(Integer id_username) {
        imagenes = new ArrayList<Imagenes>();
        imagenesRepository.findByUsuario(id_username).forEach(imagenes::add);

        return imagenes;
    }

    @Override
    public List<Imagenes> loadAllImages() {

        imagenes = new ArrayList<Imagenes>();

        imagenesRepository.findAll().forEach(imagenes::add);

        return imagenes;
    }

    
    @Override
    public void addImagen(Integer nomUser, MultipartFile[] file, String descripcion,String titulo) {
        imagen = new Imagenes();
        Long id;
        List<Imagenes> lista=this.loadAllImages();
        try {
            for (MultipartFile multipartFile : file) {
                
                imagen.setDescripcion(descripcion);
                imagen.setUsuario(nomUser);
                imagen.setTitulo(titulo);
                imagen.setImagen(this.getBlobImage(multipartFile));
                id=lista.get(lista.size()-1).getIdFoto()+1;
                imagen.setIdFoto(id);
                imagenesRepository.save(imagen);
            }

        } catch (Exception e) {

            System.out.println( e.getMessage());
        }
    }
    @Override
    public void updateImage(Long idFoto,Integer idUser, String descripcion, String titulo){
        Imagenes imagenModificada=new Imagenes();
        imagen = new Imagenes();
        imagen= this.loadImagenByIdFoto(idFoto);

        imagenModificada.setImagen(imagen.getImagenTrue());
        imagenModificada.setIdFoto(idFoto);
        imagenModificada.setDescripcion(descripcion);
        imagenModificada.setTitulo(titulo);
        imagenModificada.setUsuario(idUser);

        imagenesRepository.save(imagenModificada);
    }
    @Override
    public Imagenes loadImagenByIdFoto(Long idFoto){
        imagen=new Imagenes();
        imagen=imagenesRepository.findByIdFoto(idFoto).get();
        return imagen;
    }
    @Override
    public void deleteImage(Long idFoto){
        imagenesRepository.deleteById(idFoto);
    }
    private String getImg() {
       
            Context context;
            String res="";
            context=new Context();
            res=templateEngineConfig.templateEngine().process("../../static/img/noImage.txt", context);
            return res;
       
    }


    private byte[] getBlobImage(MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
            return bytes;
        } catch (IOException e) {
            
            e.printStackTrace();
        }
       
        return null;
    }
}
