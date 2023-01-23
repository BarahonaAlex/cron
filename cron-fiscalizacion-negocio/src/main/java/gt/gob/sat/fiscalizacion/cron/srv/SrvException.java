/**
 * Superintendencia de Administracion Tributaria (SAT)
 * Gerencia de Informatica
 * Desarrollo de Sistemas
 */

package gt.gob.sat.fiscalizacion.cron.srv;

/**
 *
 * @author Daniel Castillo (adcastic)
 * @version 1.0 
 * @since Jul 15, 2013
 */
public class SrvException
        extends Exception {

    /**
     * <p>Codigo para errores no contralados.</p>
     */
    public static final int ERROR_GENERAL = 1;
//______________________________________________________________________________
    /**
     * <p>Codigo para errores no contralados.</p>
     */
    public static final int ERROR_SIN_FILTROS = 2;
//______________________________________________________________________________
    /**
     * <p>No se encontro informacion para los filtros de busqueda proporcionados.</p>
     */
    public static final int ERROR_SIN_DATOS = 3;
    
    /**
     * <p>Ocurrio un error al buscar la informacion del usuario logeado.</p>
     */
    public static final int ERROR_BUSCAR_USUARIO = 12;
//______________________________________________________________________________
    
//______________________________________________________________________________
    /**
     * <p>Codigo del error ocurrido.</p>
     */
    private int codigoError;
//______________________________________________________________________________
    public SrvException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
//______________________________________________________________________________
    public SrvException(Throwable pCause) {
        super(pCause);
    }
//______________________________________________________________________________
    public SrvException(String pMessage) {
        super(pMessage);
    }
//______________________________________________________________________________
    public SrvException(int pCodigoError) {
        this.codigoError = pCodigoError;
    }
//______________________________________________________________________________
    public SrvException(int pCodigoError, Throwable pCause) {
        super(pCause);
        this.codigoError = pCodigoError;
    }
//______________________________________________________________________________
    public int getCodigoError() {
        return codigoError;
    }
//______________________________________________________________________________
    public void setCodigoError(int pCodigoError) {
        this.codigoError = pCodigoError;
    }
}
