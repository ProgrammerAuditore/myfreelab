/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dto;

/**
 *
 * @author victo
 */
public class FreelancerDto {
    
    private int idFreelancer;
    private String strNombre;
    private String strTelefono;
    private String strDireccion;
    private String strCorreo;

    public FreelancerDto() {
    }

    public FreelancerDto(int idFreelancer, String strNombre, String strTelefono, String strDireccion, String strCorreo) {
        this.idFreelancer = idFreelancer;
        this.strNombre = strNombre;
        this.strTelefono = strTelefono;
        this.strDireccion = strDireccion;
        this.strCorreo = strCorreo;
    }

    public int getIdFreelancer() {
        return idFreelancer;
    }

    public void setIdFreelancer(int idFreelancer) {
        this.idFreelancer = idFreelancer;
    }

    public String getStrNombre() {
        return strNombre;
    }

    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    public String getStrTelefono() {
        return strTelefono;
    }

    public void setStrTelefono(String strTelefono) {
        this.strTelefono = strTelefono;
    }

    public String getStrDireccion() {
        return strDireccion;
    }

    public void setStrDireccion(String strDireccion) {
        this.strDireccion = strDireccion;
    }

    public String getStrCorreo() {
        return strCorreo;
    }

    public void setStrCorreo(String strCorreo) {
        this.strCorreo = strCorreo;
    }

}
