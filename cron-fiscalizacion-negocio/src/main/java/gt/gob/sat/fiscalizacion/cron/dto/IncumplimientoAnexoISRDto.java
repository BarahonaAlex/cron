/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.dto;

import java.math.BigDecimal;

/**
 *
 * @author emalvarb
 */
public class IncumplimientoAnexoISRDto {
    private BigDecimal anioFiscal;
    private String nit;
    private String nombre;
    private String email;
    private String direccion;

    /**
     * @return the anioFiscal
     */
    public BigDecimal getAnioFiscal() {
        return anioFiscal;
    }

    /**
     * @param pAnioFiscal the anioFiscal to set
     */
    public void setAnioFiscal(BigDecimal pAnioFiscal) {
        this.anioFiscal = pAnioFiscal;
    }

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param pNit the nit to set
     */
    public void setNit(String pNit) {
        this.nit = pNit;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param pNombre the nombre to set
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param pEmail the email to set
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param pDireccion the direccion to set
     */
    public void setDireccion(String pDireccion) {
        this.direccion = pDireccion;
    }
    
    
}
