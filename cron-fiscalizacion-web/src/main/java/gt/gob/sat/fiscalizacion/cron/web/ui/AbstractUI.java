/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.web.ui;

import gt.gob.sat.fiscalizacion.fisat.srv.GeneralSrv;
import gt.gob.sat.fiscalizacion.cron.srv.CronSrv;
import gt.gob.sat.fiscalizacion.cron.web.util.SessionUtil;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author jaixacra
 */
public class AbstractUI {

    /**
     * <p>
     * Numero de version de la clase</p>
     */
    private static final long serialVersionUID = 1L;
//_____________________________________________________________________________

    /**
     * <p>
     * Permite acceder a propiedades de la sesion del usuario.</p>
     */
    @ManagedProperty("#{sessionUtil}")
    protected SessionUtil sessionUtil;
//______________________________________________________________________________

    /**
     * <p>
     * Permite acceder al resource bundle cargado por JSF.</p>
     */
    @ManagedProperty("#{msgWeb}")
    protected ResourceBundle msgWeb;

//______________________________________________________________________________
   

    //_____________________________________________________________________________
    /**
     * <p>
     * Servicio gestionado por Spring, contiene la logica del negocio.</p>
     */
    @ManagedProperty("#{generalSrvImpl}")
    private GeneralSrv generalSrvImpl;

    //_____________________________________________________________________________
    /**
     * <
     * p>
     * Servicio gestionado por Spring, contiene la logica del negocio.</p>
     */
    @ManagedProperty("#{servicioCron}")
    private CronSrv servicioCron;

    /**
     * <p>
     * Constructor predeterminado de la clase. Realiza la asignacion de memoria
     * de los atributos de la clase</p>
     */
    public AbstractUI() {
        //    this.panelNotificaciones = new PanelNotificaciones();
    }

    /**
     * <p>
     * Este metodo se encarga de colocar los atributos de la clase en su valor
     * predeterminado.</p>
     */
    public void limpiar() {
        init();
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este metodo se encarga de cerrar el panel de notificaciones.</p>
     */
    public void cerrarPanelNotificaciones() {
        //   this.panelNotificaciones.init();
        //   this.archivoDescarga = null;
    }

//______________________________________________________________________________
    /**
     * View Info Message
     *
     * @param pMessage - General message
     */
    public void info(String pMessage) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, pMessage, null));
    }

    /**
     * View Info Message
     *
     * @param pMessage - General message
     */
    public void warn(String pMessage) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, pMessage, null));
    }

    /**
     * View Error Message
     *
     * @param pMessage - General message
     */
    public void error(String pMessage) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, pMessage, null));

    }

    public SessionUtil getSessionUtil() {
        return sessionUtil;
    }
//______________________________________________________________________________

    public void setSessionUtil(SessionUtil pSessionUtil) {
        this.sessionUtil = pSessionUtil;
    }
//______________________________________________________________________________

    public ResourceBundle getMsgWeb() {
        return msgWeb;
    }
//______________________________________________________________________________

    public void setMsgWeb(ResourceBundle pMsgWeb) {
        this.msgWeb = pMsgWeb;
    }


    /**
     * @return the generalSrvImpl
     */
    public GeneralSrv getGeneralSrvImpl() {
        return generalSrvImpl;
    }

    /**
     * @param pGeneralSrvImpl the generalSrvImpl to set
     */
    public void setGeneralSrvImpl(GeneralSrv pGeneralSrvImpl) {
        this.generalSrvImpl = pGeneralSrvImpl;
    }

    public CronSrv getServicioCron() {
        return servicioCron;
    }

    public void setServicioCron(CronSrv pServicioCron) {
        this.servicioCron = pServicioCron;
    }

    /**
     * <p>
     * Metodo de inicializacion, coloca los atributos de la clase a su valor
     * predeterminado.</p>
     */
    protected void init() {
        //  this.panelNotificaciones.init();
    }

    /**
     * <p>
     * Este metodo se utiliza para llenar combo box.</p>
     *
     * @param pListaValores Lista que contiene los valores que se colocaran en
     * el combo box, cada posicion debe ser un string con dos valores separados
     * por un guion
     * @param pListaComboBox Lista que corresponde al combo box que se muestra
     * en la pagina
     * @param pSplit Indica si se debe separar el valor
     */
    protected void llenarComboBox(List<String> pListaValores, List<SelectItem> pListaComboBox, boolean pSplit) {
        if (pListaComboBox.isEmpty()) {
            pListaComboBox.add(new SelectItem("", ""));
            for (String valor : pListaValores) {
                if (pSplit) {
                    String[] str = valor.split("-");
                    pListaComboBox.add(new SelectItem(str[0], valor));
                } else {
                    pListaComboBox.add(new SelectItem(valor, valor));
                }
            }
        }
    }
}
