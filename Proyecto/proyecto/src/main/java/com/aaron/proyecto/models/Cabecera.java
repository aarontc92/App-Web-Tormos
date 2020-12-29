package com.aaron.proyecto.models;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;	
import javax.persistence.GeneratedValue;	
import javax.persistence.GenerationType;	
import javax.persistence.Id;	
import javax.persistence.Table;


@Entity
@Table(name="cabecera")
public class Cabecera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cabecera")
    Integer idCabecera;
    @Column(name="nom_cab")
    String nomCab;
    @Column(name="cap_usu")
    Blob capUsu;

    public Integer getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(Integer idCabecera) {
        this.idCabecera = idCabecera;
    }

    public String getNomCab() {
        return nomCab;
    }

    public void setNomCab(String nomCab) {
        this.nomCab = nomCab;
    }

    public Blob getCapUsu() {
        return capUsu;
    }

    public void setCapUsu(Blob capUsu) {
        this.capUsu = capUsu;
    }

    
}
