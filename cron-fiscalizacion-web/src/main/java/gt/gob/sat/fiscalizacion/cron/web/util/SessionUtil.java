/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.web.util;

import gt.gob.sat.fiscalizacion.fisat.dto.UsuarioDto;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import net.sf.uadetector.ReadableUserAgent;

/**
 *
 * @author jaixacra
 */
@ManagedBean(name = "sessionUtil")
@SessionScoped
public class SessionUtil implements Serializable {

    /**
     * <p>
     * Numero de version de la clase</p>
     */
    private static final long serialVersionUID = 1L;
//______________________________________________________________________________
    /**
     * <p>
     * Genera mensajes de log para depuracion.</p>
     */
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SessionUtil.class);
//______________________________________________________________________________
    /**
     * <p>
     * Permite acceder al resource bundle cargado por JSF.</p>
     */
    @ManagedProperty("#{msgWeb}")
    private ResourceBundle msgWeb;
//______________________________________________________________________________
    /**
     * <p>
     * Sesion que se inicia.</p>
     */
    @ManagedProperty("#{session}")
    private HttpSession session;
//______________________________________________________________________________

    /**
     * <p>
     * Constructor predeterminado de la clase.</p>
     */
    public SessionUtil() {

    }
//______________________________________________________________________________

    /**
     * <p>
     * Metodo de inicializacion, esta anotado con {@link PostConstruct} lo que
     * implica sera llamado despues del constructor de cada instancia de la
     * clase.</p>
     */
    @PostConstruct
    public void init() {
        LOG.info("post-construct: SessionUtil");
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este metodo permite agregar mensajes de error generales a la pagina web
     * en uso.</p>
     *
     * @param pIdComponente Componente al que se debe de asociar el mensaje,
     * debe de incluir el nombre del form al que pertenece el componente
     * @param pKeyMensaje Texto que se quiere agregar a los mensajes de error
     */
    public void agregarMensajeError(String pIdComponente, String pKeyMensaje) {
        String mensaje = this.msgWeb.getString(pKeyMensaje);
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
        FacesContext.getCurrentInstance().addMessage(pIdComponente, fm);
    }
//______________________________________________________________________________

    public UsuarioDto getUsuarioDto() {
        //return (UsuarioDto) this.session.getAttribute(Constante.ATTR_BEAN_USUARIO);
        return (UsuarioDto) this.session.getAttribute("SAT_USUARIO_COMPLETO");

    }
//______________________________________________________________________________

    public void setUsuarioDto(UsuarioDto pUserdto) {
        //this.session.setAttribute(Constante.ATTR_BEAN_USUARIO, pUserdto);
        this.session.setAttribute("SAT_USUARIO_COMPLETO", pUserdto);
    }
//______________________________________________________________________________

    public ReadableUserAgent getUserAgentInfo() {
        // return (ReadableUserAgent) this.session.getAttribute(Constante.ATTR_BEAN_USER_AGENT);
        return (ReadableUserAgent) this.session.getAttribute("SAT_USER_AGENT");
    }
//______________________________________________________________________________

    public boolean isExploradorIncompatible() {
        if (this.getUserAgentInfo().getName().contains("IE")) {
            return true;
        } else {
            return false;
        }
    }
//______________________________________________________________________________

    public ResourceBundle getMsgWeb() {
        return msgWeb;
    }
//______________________________________________________________________________

    public void setMsgWeb(ResourceBundle pMsgWeb) {
        this.msgWeb = pMsgWeb;
    }
//______________________________________________________________________________

    public HttpSession getSession() {
        return session;
    }
//______________________________________________________________________________

    public void setSession(HttpSession pSession) {
        this.session = pSession;
    }
}
