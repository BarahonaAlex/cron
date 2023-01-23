/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.exception;

import java.io.Serializable;

/**
 *
 * @author rabaraho
 */
public class DBException extends Exception implements Serializable {

    private static final long serialVersionUID = 6569209771104397671L;

    public DBException() {
        super();
    }

    public DBException(String pMessage, Throwable pCcause) {
        super(pMessage, pCcause);
    }

    public DBException(String pMessage) {
        super(pMessage);
    }

    public DBException(Throwable pCause) {
        super(pCause);
    }

}
