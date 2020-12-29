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
@Table(name="fotoscuerpo")
public class Imagenes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_foto")
    Integer idFoto;
    @Column(name="imagen")
    @Lob
    byte[] imagen;
    @Column(name="usuario")
    String usuario;
    @Column(name="descripcion")
    String descripcion;

    public Integer getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Integer idFoto) {
        this.idFoto = idFoto;
    }

    public String getImagen() {
        String resultado="";
        try{
           
            resultado=Base64.encodeBase64String(imagen);
        }catch(Exception e){
            resultado= "";
         }
        return resultado;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
