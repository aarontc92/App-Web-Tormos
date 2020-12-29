package com.aaron.proyecto.Service;

import java.util.List;

import com.aaron.proyecto.models.Imagenes;

import org.springframework.web.multipart.MultipartFile;



public interface ImagenesService {
    public List<String> loadImagesByUsername(String username);
    public List<Imagenes> loadAllImages();
    public List<Imagenes> loadDatos(String username);
	public void addImagen(String nomUser, MultipartFile[] file, String descripcion);
}
