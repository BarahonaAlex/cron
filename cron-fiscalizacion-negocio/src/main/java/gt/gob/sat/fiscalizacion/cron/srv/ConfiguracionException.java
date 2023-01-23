/**
 * Superintendencia de Administracion Tributaria (SAT)
 * Gerencia de Informatica
 * Desarrollo de Sistemas
 */

package gt.gob.sat.fiscalizacion.cron.srv;

/**
 * <p>Esta excepcion se utiliza cuando no es posible realizar la configuracion
 * inicial de algun servicio.</p>
 * 
 * @author Daniel Castillo (adcastic)
 * @version 1.0 
 * @since May 9, 2014
 */
public class ConfiguracionException 
        extends RuntimeException {

    /**
     * <p>Numero de version de la clase</p>
     */
    private static final long serialVersionUID = 1L;
//______________________________________________________________________________
    /**
     * <p>Crea una nueva excepcion de tiempo de ejecucion con el mensaje de error
     * y causa indicadas.</p>
     * 
     * @param pMessage Mensaje de descripcion del error
     * @param pCause Causa del error
     */
    public ConfiguracionException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}
