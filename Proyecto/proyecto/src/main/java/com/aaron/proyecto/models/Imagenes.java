package com.aaron.proyecto.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;

@Entity
@Table(name = "fotoscuerpo")
public class Imagenes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto")
    Long idFoto;
    @Column(name = "imagen")
    @Lob
    byte[] imagen;
    @Column(name = "id_usuario_foto")
    Integer usuario;
    @Column(name = "descripcion")
    String descripcion;
    @Column(name = "titulo")
    String titulo;

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }

    public String getImagen() {
        String resultado = "";
        try {
            resultado = Base64.encodeBase64String(imagen);
        } catch (Exception e) {
            resultado = "";
        }
        return resultado;
    }
    public byte[] getImagenTrue() {
        return imagen;
    }
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    

}
