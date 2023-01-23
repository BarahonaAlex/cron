/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.srv;

import gt.gob.sat.fiscalizacion.cron.dto.BitacoraNotificacionDto;
import gt.gob.sat.fiscalizacion.cron.dto.DestinatarioDto;
import gt.gob.sat.fiscalizacion.cron.dto.NotificacionDto;
import gt.gob.sat.fiscalizacion.cron.dto.RegistroBitacoraDto;
import gt.gob.sat.fiscalizacion.fisat.modelo.sat_fiscalizacion.IptBitacoraNotificacion;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 *
 * @author emalvarb
 */
public interface CronSrv {

    /**
     * <p>
     * Retorna las transacciones realizadas en el rango indicado de fechas  </p>
     *
     * @param pFechaInicial limite inferior
     * @param pFechaFinal limite superior
     * @return Lista de impuestos de la tabla PAPELES_ESTADO
     */
    public List<RegistroBitacoraDto> getHistoricoTransaccion(Date pFechaInicial, Date pFechaFinal);

    /**
     * <p>
     * permite obtener cualquier notificacion para ser enviada por correo</p>
     *
     * @param pCodigo: codigo unico para cada notificacion
     * @return registro identificado con el código
     * @throws java.lang.Exception
     */
    public NotificacionDto obtenerNotificacionSinAnexo(String pCodigo) throws Exception;

    /**
     * <p>
     * Devuelve los contribuyentes que no presentaron Anexo a la declaracion
     * anual del ISR [PRECIOS DE TRANSFERENCIA]</p>
     *
     * @return contribuyentes que no presentaron Anexo
     * @throws java.lang.Exception
     */
    public DestinatarioDto getIncumplimientoAnexoISR() throws Exception;

    /**
     * <p>
     * notifica por correo electronico a quienes no presentaron el anexo al
     * ISR</p>
     */
    public void notificacionIncumplimientoAnexo();

    
    public BigDecimal getSecuencia(String pNombreSecuencia);

    /**
     * <p>
     * Llena un Entity para almacenar en un collection</p>
     *
     * @param pRegistro: contiene todos los datos para formar el entity
     * @return
     */
    public IptBitacoraNotificacion getRegistroBitacora(BitacoraNotificacionDto pRegistro);

    /**
     * <p>
     * Registra todas las notificaciones enviadas</p>
     *
     * @param pNotificacionesEnviadas
     */
    public void almacenarHistoricoNotificacion(Collection pNotificacionesEnviadas);

    public SessionFactory getSessionFactory();

    public void setSessionFactory(SessionFactory pSessionFactory);
}
