package com.aaron.proyecto.Service;

import java.util.List;

import com.aaron.proyecto.models.Imagenes;

import org.springframework.web.multipart.MultipartFile;



public interface ImagenesService {
    public List<String> loadImagesByUsername(Integer username);
    public Imagenes loadImagenByIdFoto(Long idFoto);
    public List<Imagenes> loadAllImages();
    public List<Imagenes> loadDatos(Integer id_username);
    public void addImagen(Integer nomUser, MultipartFile[] file, String descripcion,String titulo);
    public void deleteImage(Long idFoto);
    public void updateImage(Long idFoto,Integer idUser, String descripcion, String titulo);
}
