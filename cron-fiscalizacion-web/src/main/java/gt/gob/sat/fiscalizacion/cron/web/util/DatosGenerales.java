package gt.gob.sat.fiscalizacion.cron.web.util;

import java.text.SimpleDateFormat;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Obtiene la información relativa al usuario logeado y a la fecha/hora de uso
 * de una página
 *
 * @author jacordov
 * @version 0.0.1
 * @FechaCreacion 15/03/2012
 */
public class DatosGenerales {

    private String usuario;
    private String opcion;
    private String sesion;
    private String ticket;

    private String fecha;

    public String getUsuario() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("SAT_SEGURIDAD_login") != null) {
            usuario = session.getAttribute("SAT_SEGURIDAD_login").toString();

            return ((usuario != null && !usuario.equals("")) ? usuario : "Invitado");
        } else {
            return "Invitado";
        }
    }

    public void setUsuario(String pUsuario) {
        this.usuario = pUsuario;
    }

    public String getOpcion() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("SAT_SEGURIDAD_opcion") != null) {
            opcion = session.getAttribute("SAT_SEGURIDAD_opcion").toString();

            return ((opcion != null && !opcion.equals("")) ? "Opcion: " + opcion : "Invitado");
        } else {
            return "Invitado";
        }
    }

    public void setOpcion(String pOpcion) {
        this.opcion = pOpcion;
    }

    public String getSesion() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("SAT_SEGURIDAD_sessionGlobal") != null) {
            sesion = session.getAttribute("SAT_SEGURIDAD_sessionGlobal").toString();
            /*if(session.getAttribute("SAT_SEGURIDAD_ticket") != null) {
             sesion = session.getAttribute("SAT_SEGURIDAD_ticket").toString();*/

            return ((sesion != null && !sesion.equals("")) ? "Sesion: " + sesion : "Invitado");
        } else {
            return "Invitado";
        }

        /*Enumeration keys = session.getAttributeNames();
         String todolinea="";
         while (keys.hasMoreElements())
         {
         String key = (String)keys.nextElement();
         System.out.println(key + ": " + session.getValue(key) + "<br>");
         todolinea = todolinea + key;
         }
         return todolinea;*/
    }

    public String getTicket() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session.getAttribute("SAT_SEGURIDAD_ticket") != null) {
            sesion = session.getAttribute("SAT_SEGURIDAD_ticket").toString();

            return ((sesion != null && !sesion.equals("")) ? sesion : "Invitado");
        } else {
            return "Invitado";
        }

    }

    public void setSesion(String pSesion) {
        this.sesion = pSesion;
    }

    public void setTicket(String pTicket) {
        this.ticket = pTicket;
    }

    public String getTicket(String pTicket) {
        return this.ticket;
    }

    public String getFecha() {
        java.util.Date ahora = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        fecha = sdf.format(ahora);
        return fecha;
    }

    public void setFecha(String pFecha) {
        this.fecha = pFecha;
    }

    public static void error(Exception pE) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, pE.getMessage(), null));
    }

    public static void mensaje(String pInformacion) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(pInformacion));
    }

    protected void finalize() {
        this.fecha = null;
        this.usuario = null;
    }
}
