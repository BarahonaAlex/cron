/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.dto;

import java.util.Date;

/**
 *
 * @author emalvarb
 */
public class RegistroBitacoraDto {
    
    private String usuario;
    private String operacion;
    private Date fechaOperacion;
    private String comentario;
    private String fechaTransaccion;

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param pUsuario the usuario to set
     */
    public void setUsuario(String pUsuario) {
        this.usuario = pUsuario;
    }

    /**
     * @return the operacion
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * @param pOperacion the operacion to set
     */
    public void setOperacion(String pOperacion) {
        this.operacion = pOperacion;
    }

    /**
     * @return the fechaOperacion
     */
    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * @param pFechaOperacion the fechaOperacion to set
     */
    public void setFechaOperacion(Date pFechaOperacion) {
        this.fechaOperacion = pFechaOperacion;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param pComentario the comentario to set
     */
    public void setComentario(String pComentario) {
        this.comentario = pComentario;
    }

    /**
     * @return the fechaTransaccion
     */
    public String getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * @param pFechaTransaccion the fechaTransaccion to set
     */
    public void setFechaTransaccion(String pFechaTransaccion) {
        this.fechaTransaccion = pFechaTransaccion;
    }
    
    
}
