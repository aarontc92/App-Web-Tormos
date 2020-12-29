package com.aaron.proyecto.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.aaron.proyecto.models.Imagenes;
import com.aaron.proyecto.repository.ImagenesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenesServiceImpl implements ImagenesService {
    @Autowired
    ImagenesRepository imagenesRepository;
    Imagenes imagen;
    List<Imagenes> imagenes;
    List<String> datoImagen;
    String resultado = "";

    @Override
    public List<String> loadImagesByUsername(String username) {
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
    public List<Imagenes> loadDatos(String username) {
        imagenes = new ArrayList<Imagenes>();
        imagenesRepository.findByUsuario(username).forEach(imagenes::add);

        return imagenes;
    }

    @Override
    public List<Imagenes> loadAllImages() {

        imagenes = new ArrayList<Imagenes>();
        imagenesRepository.findAll().forEach(imagenes::add);

        return imagenes;
    }

    private String getImg() {
        String resultado = "";

        try {
            File archivo = new File("./src/main/resources/static/img/noImage.txt");
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null)
                resultado += linea;
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    @Override
    public void addImagen(String nomUser, MultipartFile[] file, String descripcion) {
        imagen = new Imagenes();
        try {
            for (MultipartFile multipartFile : file) {
               
                imagen.setDescripcion(descripcion);
                imagen.setUsuario(nomUser);
                imagen.setImagen(this.getBlobImage(multipartFile));
                int id = this.loadAllImages().size() + 1;
                imagen.setIdFoto(id);
                imagenesRepository.save(imagen);
            }

        } catch (Exception e) {

            System.out.println("Aaron" + e.getMessage());
        }
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
