/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author emalvarb
 */
public class BitacoraNotificacionDto {
    private String nit;
    private BigDecimal numeroNotificacion;
    private Date fecha;
    private String email;

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
     * @return the No_notificacion
     */
    public BigDecimal getNumeroNotificacion() {
        return numeroNotificacion;
    }

    /**
     * @param pNoNotificacion the No_notificacion to set
     */
    public void setNumeroNotificacion(BigDecimal pNoNotificacion) {
        this.numeroNotificacion = pNoNotificacion;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param pFecha the fecha to set
     */
    public void setFecha(Date pFecha) {
        this.fecha = pFecha;
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
    
}
