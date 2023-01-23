/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.dto;

/**
 *
 * @author emalvarb
 */
public class NotificacionDto {

    private long idNotificacion;
    private String aplicacion;
    private String descripcion;
    private String destinatario;
    private String cuerpo;
    private String formatoHtml;
    private String codigo;
    private String asunto;

    /**
     * @return the idNotificacion
     */
    public long getIdNotificacion() {
        return idNotificacion;
    }

    /**
     * @param pIdNotificacion the idNotificacion to set
     */
    public void setIdNotificacion(long pIdNotificacion) {
        this.idNotificacion = pIdNotificacion;
    }

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @param pAplicacion the aplicacion to set
     */
    public void setAplicacion(String pAplicacion) {
        this.aplicacion = pAplicacion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param pDescripcion the descripcion to set
     */
    public void setDescripcion(String pDescripcion) {
        this.descripcion = pDescripcion;
    }

    /**
     * @return the destinatario
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * @param pDestinatario the destinatario to set
     */
    public void setDestinatario(String pDestinatario) {
        this.destinatario = pDestinatario;
    }

    /**
     * @return the cuerpo
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * @param pCuerpo the cuerpo to set
     */
    public void setCuerpo(String pCuerpo) {
        this.cuerpo = pCuerpo;
    }

    /**
     * @return the formatoHtml
     */
    public String getFormatoHtml() {
        return formatoHtml;
    }

    /**
     * @param pFormatoHtml the formatoHtml to set
     */
    public void setFormatoHtml(String pFormatoHtml) {
        this.formatoHtml = pFormatoHtml;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param pCodigo the codigo to set
     */
    public void setCodigo(String pCodigo) {
        this.codigo = pCodigo;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param pAsunto the asunto to set
     */
    public void setAsunto(String pAsunto) {
        this.asunto = pAsunto;
    }

}
