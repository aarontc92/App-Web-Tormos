package com.aaron.proyecto.Service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.aaron.proyecto.models.Cabecera;
import com.aaron.proyecto.repository.CabeceraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.codec.binary.Base64;

@Service
public class CabeceraServiceImpl implements CabeceraService {
    @Autowired
    CabeceraRepository cabecera;

    @Override
    public String loadCabeceraByUsername(String username) {
        String resultado="";
        try{
            
           Cabecera imagen= cabecera.findByNomCab(username).get();
           byte[] img=imagen.getCapUsu().getBytes(1,(int)imagen.getCapUsu().length());
           resultado=Base64.encodeBase64String(img);
        }catch(Exception e){
           resultado= this.getImgUser();
        }
        
        return resultado;
    }

    private String getImgUser()  {
        String resultado="";
        
        try{
            File archivo = new File ("./src/main/resources/static/img/user.txt");
        FileReader fr = new FileReader (archivo);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        while((linea=br.readLine())!=null)
            resultado+=linea;
        br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return resultado;
    }
   
}
