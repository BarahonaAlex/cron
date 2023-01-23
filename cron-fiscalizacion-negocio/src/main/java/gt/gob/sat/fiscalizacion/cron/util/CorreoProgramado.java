/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.util;

/**
 *
 * @author emalvarb
 */
import gt.gob.sat.aduanas.utilidades.dto.ArchivoAdjuntoDto;
import gt.gob.sat.aduanas.utilidades.dto.CorreoDto;
import gt.gob.sat.fiscalizacion.cron.dto.DestinatarioDto;
import gt.gob.sat.fiscalizacion.cron.dto.IncumplimientoAnexoISRDto;
import gt.gob.sat.fiscalizacion.cron.dto.NotificacionDto;
import gt.gob.sat.fiscalizacion.cron.srv.CronSrv;
import gt.gob.sat.fiscalizacion.fisat.modelo.sat_fiscalizacion.IptBitacoraNotificacion;
import gt.gob.sat.fiscalizacion.fisat.modelo.sat_fiscalizacion.IptBitacoraNotificacionId;
import gt.gob.sat.fiscalizacion.fisat.srv.GeneralSrv;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CorreoProgramado {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CorreoProgramado.class);

    @Resource
    private GeneralSrv generalSrv;

    @Resource
    private CronSrv servicioCron;

    /**
     * <p>
     * PRECIOS DE TRANSFERENCIA: envía correo a los contribuyentes que no
     * llenaron el anexo a la declaración jurada anual del ISR sobre partes
     * relacionadas
     * </p>
     */
    @Scheduled(cron = Constante.PT_VENCIMIENTO_PRESENTACION_ANEXO)
    public void enviarNotificacionAnexo() {
        LOG.info("[INICIANDO ENVIO DE CORREO AUTOMATICO]: PRECIOS DE TRANSFERENCIA-OMISION ANEXO ISR");
        NotificacionDto estructuraNotificacion = getEstructuraNotificacion();

        if (estructuraNotificacion != null) {
            DestinatarioDto contribuyentes = null;
            try {
                contribuyentes = this.getServicioCron().getIncumplimientoAnexoISR();
            } catch (Exception ex) {
                LOG.error("ERROR AL OBTENER LISTADO DE CONTRIBUYENTES " + ex);
            }
            if (contribuyentes != null) {
                String nit, periodoFiscal, email;
                String nombreContribuyente, direccion, destinatario;
                BigDecimal numeroNotificacion;
                Collection registroBitacora = new ArrayList<>();
                StringBuilder contribuyentesSinNotificar = new StringBuilder();

                for (IncumplimientoAnexoISRDto incumplimiento : contribuyentes.getDatosContribuyentes()) {
                    nit = incumplimiento.getNit();
                    nombreContribuyente = incumplimiento.getNombre();
                    direccion = (StringUtils.isNotBlank(incumplimiento.getDireccion())) ? incumplimiento.getDireccion() : "";
                    periodoFiscal = incumplimiento.getAnioFiscal().toString();

                    if (incumplimiento.getEmail() != null) {
                        email = incumplimiento.getEmail();
                    } else {
                        email = getCorreoContribuyente(nit, contribuyentes);
                    }
                    if (email != null) {
                        //cambio por cada correo
                        destinatario = estructuraNotificacion.getDestinatario()
                                .replace(Constante.NIT_CONTRIBUYENTE, nit)
                                .replace(Constante.NOMBRE, nombreContribuyente)
                                .replace(Constante.DIRECCION, direccion);

                        numeroNotificacion = servicioCron.getSecuencia(Constante.NOM_SEQ_CORRELATIVO);
                        String notificacionHtml = estructuraNotificacion.getFormatoHtml().replace(Constante.DESTINATARIO, destinatario)
                                .replace(Constante.PERIODO, periodoFiscal)
                                .replace(Constante.NO_NOTIFICACION, numeroNotificacion.toString());

                        try {
                            this.getGeneralSrvImpl().enviarNotificacionElectronica(construirCorreo(
                                    estructuraNotificacion.getAsunto(), notificacionHtml, email, true, null));

                            registroBitacora.add(getEntityBitacora(nit, numeroNotificacion, email));
                        } catch (java.lang.NullPointerException ex) {
                            LOG.error("[ERRO-ENVIO_EMAIL]: Error al enviar correo" + ex);
                        } catch (Exception e) {
                            LOG.error("[EXCEPTION-ENVIO_EMAIL]: Error al enviar correo" + e);
                        }
                    } else {
                        contribuyentesSinNotificar.append(nit).append(" , ");
                    }
                }
                servicioCron.almacenarHistoricoNotificacion(registroBitacora);
                LOG.error("[CONTRIBUYENTES SIN NOTIFICAR]: No poseen email: " + contribuyentesSinNotificar.toString());

            } else {
                LOG.info("[ENVIO DE CORREO AUTOMATICO: NO SE ENCONTRARON OMISIONES EN PRESENTACION DE ANEXO AL ISR]");
            }
        } else {
            LOG.error("[ENVIO DE CORREO AUTOMATIO: NO SE OBTUVO LA ESTRUCTURA DEL MENSAJE]");
        }
        LOG.info("[ENVIO DE CORREO AUTOMATICO FINALIZADO]: PRECIOS DE TRANSFERENCIA");
    }

    public NotificacionDto getEstructuraNotificacion() {
        NotificacionDto notificacion = null;
        try {
            notificacion = servicioCron.obtenerNotificacionSinAnexo(Constante.CODIGO_OMISION_ANEXO);
            byte[] cuerpoCodificado = notificacion.getCuerpo().getBytes("UTF-8");

            Calendar fecha = Calendar.getInstance();
            int periodo = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH) + 1;
            String fechaActual = String.valueOf(fecha.get(Calendar.DATE) + "/" + mes + "/" + periodo);
            notificacion.setFormatoHtml(notificacion.getFormatoHtml().replace(Constante.FECHA_ACTUAL, fechaActual).
                    replace(Constante.CONTENIDO_NOTIFICACION, new String(cuerpoCodificado, "UTF-8")));
            LOG.info("[SE HA OBTENIDO CORRECTAMENTE LA ESTRUCTURA DE LA NOTIFICACION]");
        } catch (Exception ex) {
            LOG.error("ERROR AL OBTENER EL CONTENIDO DE LA NOTIFICACION " + ex);
        }
        return notificacion;
    }

    //[correo para notificar], buscando en (BANCOS_CONTRATOS Y SAT EN LINEA) para notificar 
    public String getCorreoContribuyente(String pNit, DestinatarioDto pContribuyentes) {
        for (IncumplimientoAnexoISRDto correoEnBanco : pContribuyentes.getCorreosEnBancos()) {
            if (pNit.equals(correoEnBanco.getNit())) {
                return correoEnBanco.getEmail();
            }
        }
        for (IncumplimientoAnexoISRDto correoSatEnLinea : pContribuyentes.getCorreosEnSatEnLinea()) {
            if (pNit.equals(correoSatEnLinea.getNit())) {
                return correoSatEnLinea.getEmail();
            }
        }
        return null;
    }

    public IptBitacoraNotificacion getEntityBitacora(String pNit, BigDecimal pNotificacion, String pEmail) {
        IptBitacoraNotificacionId notificacionId = new IptBitacoraNotificacionId();
        notificacionId.setNit(pNit);
        notificacionId.setNoNotificacion(pNotificacion);

        IptBitacoraNotificacion notificacion = new IptBitacoraNotificacion();
        notificacion.setId(notificacionId);
        notificacion.setFecha(new Date());
        notificacion.setCorreoNotificado(pEmail);

        return notificacion;
    }

    public CorreoDto construirCorreo(String pAsunto, String pMensaje, String pDestinatarios, boolean pFormatoHtml, ArchivoAdjuntoDto pArchivoAdjunto) {
        CorreoDto datos = new CorreoDto();
        try {
            datos.setAsunto(pAsunto);
            datos.setContenido(pMensaje);
            datos.setDestinatarios(pDestinatarios);
            datos.setHtml(pFormatoHtml);
            datos.setRemitente("fiscalizacion@sat.gob.gt");
            datos.setSeparadorDestinatarios(";");
            if (pArchivoAdjunto != null) {
                datos.setArchivoAdjunto(pArchivoAdjunto);
            }
        } catch (Throwable ex) {
            LOG.error("Error Al Construir Correo" + ex);
        }
        return datos;
    }

    /*
     //descomentar si en dado caso se utiliza archivo adjunto en las notificaciones
     private ArchivoAdjuntoDto obtenerArchivoAdjunto(byte[] pArchivo, String pNombreArchivo, String pTipoArchivo) {
     ArchivoAdjuntoDto archivo = new ArchivoAdjuntoDto();
     if (pArchivo.length > 0) {
     archivo.setBytes(pArchivo);
     String nameArchivo = null;
     if (!pTipoArchivo.isEmpty()) {
     archivo.setTipo(pTipoArchivo);
     if (pNombreArchivo.isEmpty()) {
     nameArchivo = "archivo" + "." + pTipoArchivo.substring(pTipoArchivo.indexOf("/") + 1, pTipoArchivo.length());
     } else if (!pNombreArchivo.contains(".")) {
     nameArchivo = pNombreArchivo + "." + pTipoArchivo.substring(pTipoArchivo.indexOf("/") + 1, pTipoArchivo.length());
     }
     } else {
     LOG.error("El Tipo De Archivo Es Necesario");
     }
     archivo.setNombre(nameArchivo);
     return archivo;
     } else {
     LOG.error("Error El Archivo Tiene 0 Bytes");
     return null;
     }
     }
     */
    /**
     * @return the generalSrv
     */
    public GeneralSrv getGeneralSrvImpl() {
        return generalSrv;
    }

    /**
     * @param pGeneralSrv the generalSrv to set
     */
    public void setGeneralSrvImpl(GeneralSrv pGeneralSrv) {
        this.generalSrv = pGeneralSrv;
    }

    /**
     * @return the servicioCron
     */
    public CronSrv getServicioCron() {
        return servicioCron;
    }

    /**
     * @param pServicioCron the servicioCron to set
     */
    public void setServicioCron(CronSrv pServicioCron) {
        this.servicioCron = pServicioCron;
    }
}
