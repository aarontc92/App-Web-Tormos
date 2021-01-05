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
@Table(name = "usuario")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    Integer idUsuario;
    @Column(name = "nom_usuario")
    String nomUser;
    @Column(name = "email")
    String correo;
    @Column(name = "pass")
    String pass;
    @Column(name="cabecera")
    @Lob
    byte[] imagen;

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCabecera() {
        String resultado="";
        try{ 
            resultado=Base64.encodeBase64String(imagen);
        }catch(Exception e){
            resultado= "";
         }
        return resultado;
    }

    public void setCabecera(byte[] imagen) {
        this.imagen = imagen;
    }
    
   

    @Override
    public String toString() {
        return "Users [correo=" + correo + ", idUsuario=" + idUsuario + ", nomUser=" + nomUser + ", pass=" + pass + "]";
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    
    

}
