/**
 * Superintendencia de Administracion Tributaria (SAT) Gerencia de Informatica
 * Desarrollo de Sistemas
 */
package gt.gob.sat.fiscalizacion.cron.srv.impl;

import gt.gob.sat.aduanas.utilidades.dto.ArchivoAdjuntoDto;
import gt.gob.sat.aduanas.utilidades.dto.CorreoDto;
import gt.gob.sat.fiscalizacion.cron.dto.NotificacionDto;
import gt.gob.sat.fiscalizacion.cron.dto.RegistroBitacoraDto;
import gt.gob.sat.fiscalizacion.cron.srv.CronSrv;
import gt.gob.sat.fiscalizacion.cron.util.Constante;
import gt.gob.sat.fiscalizacion.fisat.srv.GeneralSrv;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Daniel Castillo (adcastic)
 * @version 1.0
 * @since Jul 15, 2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/applicationContextTest.xml"})
public class CronSrvImplTest {

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
    private static final Log LOGGER = LogFactory.getLog(CronSrvImplTest.class);
//______________________________________________________________________________
    /**
     * <p>
     * Bean de la capa de servicio gestionado por Spring. En los bean de
     * servicio se implemnta la mayoria de la logica del negocio.</p>
     */
   

    @Autowired
    private CronSrv generalSrvImplcore;
    
    @Autowired
    private GeneralSrv generalSrvImpl;
//______________________________________________________________________________

    /**
     * <p>
     * Constructor predeterminado.</p>
     */
    public CronSrvImplTest() {
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este metodo se ejecuta antes de iniciar con las pruebas unitarias. Este
     * metodo es util para inicializar atributos comunes a varias pruebas o para
     * ejecutar procesos que tardaran bastante tiempo en ejecutarse. Esta
     * capacidad es otorgada por la anotacion {@link BeforeClass}, tomar en
     * cuenta que solo puede existir un metodo con esta anotacion.</p>
     */
    @BeforeClass
    public static void setUpClass() {
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este método sera invocado un sola vez cuando finalizen todas las pruebas
     * unitarias. Esta capacidad es otorgada por la anotacion
     * {@link AfterClass}, tomar en cuenta que solo puede existir un metodo con
     * esta anotacion.</p>
     */
    @AfterClass
    public static void tearDownClass() {
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este metodo se ejecuta antes de iniciar cada prueba. Esta capacidad es
     * otorgada por la anotacion {@link Before}, pueden existir varios metodos
     * con esta anotacion.</p>
     */
    @Before
    public void setUp() {
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este metodo se ejecuta despues de finalizar cada prueba. Esta capacidad
     * es otorgada por la anotacion {@link After}, pueden existir varios metodos
     * con esta anotacion.</p
     */
    @After
    public void tearDown() {
    }


   @Test
    public void enviarNotificacion() {
      System.out.println("iniciando proceso");
        NotificacionDto notificacion=null;
        try {
            notificacion = this.getGeneralSrvImplcore().obtenerNotificacionSinAnexo(Constante.CODIGO_OMISION_ANEXO);
        } catch (Exception ex) {
        }
        if(notificacion !=null){
            System.out.println("notificacion "+notificacion);
        System.out.println("destinatario "+notificacion.getDestinatario());
        String destinatario=notificacion.getDestinatario().replace("{NIT_CONTRIBUYENTE}", "12345678").replace("{NOMBRE_CONTRIBUYENTE}", "Eduardo Alvarez").replace("{DIRECCION_CONTRIBUYENTE}", "zona 18");
        System.out.println("destinatario2 "+destinatario);
        
        
        }
    }

    
     public CorreoDto construirCorreo(String pAsunto, String pMensaje, String pDestinatarios, boolean pFormatoHtml, byte[] pArchivo, String pNombreArchivo, String pTipoArchivo) {
        CorreoDto datos = new CorreoDto();
        try {
            datos.setAsunto(pAsunto);
            datos.setContenido(pMensaje);
            datos.setDestinatarios(pDestinatarios);
            datos.setHtml(pFormatoHtml);
            if (pArchivo != null) {
                System.out.println("-----o----");
                if (pArchivo.length > 0) {
                    ArchivoAdjuntoDto archivo = new ArchivoAdjuntoDto();
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
                        System.out.println("El Tipo De Archivo Es Necesario");
                    }
                    archivo.setNombre(nameArchivo);
                    datos.setArchivoAdjunto(archivo);
                } else {
                    System.out.println("Error El Archivo Tiene 0 Bytes");
                }
            }
           System.out.println("------correo construido----");
        } catch (Throwable ex) {
            System.out.println("Error Al Construir Correo" + ex);
        }
        System.out.println("Retornadno paquete");
        return datos;
    }
    

    @Ignore
    @Test
    public void getHistorico() {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        String strFecha = "01-12-2015";
        Date pFechaInicial = null;
        try {
            pFechaInicial = formatoDelTexto.parse(strFecha);
            Date pFechaFinal = new Date();
            List<RegistroBitacoraDto> lista = this.generalSrvImplcore.getHistoricoTransaccion(pFechaInicial, pFechaFinal);
            System.err.println("tamaño " + lista.size());

        } catch (ParseException ex) {

            ex.printStackTrace();

        }

    }

   

    @Ignore
    @Test
    public void testGetVersion() {
        System.err.println("Version....");
        //  System.err.println("version para el codigo del formato: "+(this.generalSrvImplcore.getNoVersion("C-1")));

    }

    public CronSrv getGeneralSrvImplcore() {
        return generalSrvImplcore;
    }

    public void setGeneralSrvImplcore(CronSrv generalSrvImplcore) {
        this.generalSrvImplcore = generalSrvImplcore;
    }
    
     public GeneralSrv getGeneralSrvImpl() {
        return generalSrvImpl;
    }

    public void setGeneralSrvImpl(GeneralSrv generalSrvImpl) {
        this.generalSrvImpl = generalSrvImpl;
    }

}
