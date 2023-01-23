/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.srv.impl;

import gt.gob.sat.fiscalizacion.cron.dto.BitacoraNotificacionDto;
import gt.gob.sat.fiscalizacion.cron.dto.DestinatarioDto;
import gt.gob.sat.fiscalizacion.cron.dto.IncumplimientoAnexoISRDto;
import gt.gob.sat.fiscalizacion.cron.dto.NotificacionDto;
import gt.gob.sat.fiscalizacion.fisat.dao.DaoGeneral;
import gt.gob.sat.fiscalizacion.cron.srv.CronSrv;
import gt.gob.sat.fiscalizacion.cron.dto.RegistroBitacoraDto;
import gt.gob.sat.fiscalizacion.fisat.modelo.sat_fiscalizacion.IptBitacoraNotificacion;
import gt.gob.sat.fiscalizacion.fisat.modelo.sat_fiscalizacion.IptBitacoraNotificacionId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emalvarb
 */
@Service("servicioCron")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CronSrvImpl implements CronSrv, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Genera mensajes de log para depuracion.</p>
     */
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CronSrvImpl.class);
//______________________________________________________________________________
    /**
     * <p>
     * Servicio gestionado por Spring, permite acceso a la base de datos.</p>
     */
    @Resource
    private DaoGeneral daoGeneralImpl;

    @Autowired
    private SessionFactory sessionFactory;

//______________________________________________________________________________
    /**
     * <p>
     * Constructor predeterminado de la clase.</p>
     */
    public CronSrvImpl() {
        LOG.info("construct: servicioCronFiscalizacion");
    }
//______________________________________________________________________________

    /**
     * <p>
     * Metodo de inicializacion, se ejecuta despues del contructor y asignacion
     * de beans, se encarga de realizar configuraciones previas del
     * servicio.</p>
     */
    @PostConstruct
    public final void init() {
        LOG.info("post-construct: declaracionesSrvImpl");

    }

//______________________________________________________________________________
    public DaoGeneral getDaoGeneralImpl() {
        return daoGeneralImpl;
    }

    public void setDaoGeneralImpl(DaoGeneral pDaoGeneralImpl) {
        this.daoGeneralImpl = pDaoGeneralImpl;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void setSessionFactory(SessionFactory pSessionFactory) {
        this.sessionFactory = pSessionFactory;
    }

    @Override
    public List<RegistroBitacoraDto> getHistoricoTransaccion(Date pFechaInicial, Date pFechaFinal) {
        Map<String, Object> params = new HashMap<>();
        params.put("pFechaInicial", pFechaInicial);
        params.put("pFechaFinal", pFechaFinal);
        return this.daoGeneralImpl.findByNamedQuery("getHistoricoTransacciones", RegistroBitacoraDto.class, params);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificacionDto obtenerNotificacionSinAnexo(String pCodigo) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pCodigo", pCodigo);
        return this.daoGeneralImpl.uniqueResult("obtenerNotificacionSinAnexo", NotificacionDto.class, params);
    }

    @Override
    public void notificacionIncumplimientoAnexo() {

    }

    @Override
    public DestinatarioDto getIncumplimientoAnexoISR() throws Exception {
        DestinatarioDto destinatario = new DestinatarioDto();
        destinatario.setDatosContribuyentes(getDatosContribuyentes());
        destinatario.setCorreosEnBancos(getCorreosBancos());
        destinatario.setCorreosEnSatEnLinea(getCorreosSatEnLinea());

        return destinatario;
    }

    private List<IncumplimientoAnexoISRDto> getDatosContribuyentes() throws Exception {
        Calendar fechaActual = Calendar.getInstance();
        int mesActual = fechaActual.get(Calendar.MONTH) + 1;
        Map<String, Object> params = new HashMap<>();

        //si es antes de abril 
        if (mesActual <= (Calendar.MARCH + 1)) {
            params.put("pPeriodo", fechaActual.get(Calendar.YEAR) - 1);
        } else {
            params.put("pPeriodo", fechaActual.get(Calendar.YEAR));
        }
        return this.daoGeneralImpl.findByNamedQuery("incumplimientoAnexoISR", IncumplimientoAnexoISRDto.class, params);
    }

    private List<IncumplimientoAnexoISRDto> getCorreosBancos() throws Exception {
        return this.daoGeneralImpl.findByNamedQuery("correosBA_CONTRATOS", IncumplimientoAnexoISRDto.class, null);
    }

    private List<IncumplimientoAnexoISRDto> getCorreosSatEnLinea() throws Exception {
        return this.daoGeneralImpl.findByNamedQuery("correos_SAT_EN_LINEA", IncumplimientoAnexoISRDto.class, null);
    }

    @Override
    public BigDecimal getSecuencia(String pNombreSecuencia) {
        return this.daoGeneralImpl.getSequenceValue(pNombreSecuencia);
    }

    @Override
    public IptBitacoraNotificacion getRegistroBitacora(BitacoraNotificacionDto pRegistro) {
        IptBitacoraNotificacionId notificacionId = new IptBitacoraNotificacionId();
        notificacionId.setNit(pRegistro.getNit());
        notificacionId.setNoNotificacion(pRegistro.getNumeroNotificacion());

        IptBitacoraNotificacion notificacion = new IptBitacoraNotificacion();
        notificacion.setId(notificacionId);
        notificacion.setFecha(new Date());
        notificacion.setCorreoNotificado(pRegistro.getEmail());

        return notificacion;
    }

    @Override
    @Transactional
    public void almacenarHistoricoNotificacion(Collection pNotificacionesEnviadas) {
        LOG.info("[INICIANDO ALMACENAMIENTO DE NOTIFICACIONES ENVIADAS]");
        daoGeneralImpl.saveAll(pNotificacionesEnviadas);
        LOG.info("[NOTIFICACIONES ENVIADAS] REGISTROS ALMACENADOS: " + pNotificacionesEnviadas.size());
    }

}
