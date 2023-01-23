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
public class Constante {


    /*PARAMETROS CRON
    1.  Seconds
    2.  Minutes
    3.  Hours
    4.  Day-of-Month
    5.  Month
    6.  Day-of-Week
    7.  Year (optional field)
    
    */
    //PT_VENCIMIENTO_PRESENTACION_ANEXO: "0 0 0 6 * ?" = sexto dia de cada mes a las 00:00:00 hrs.
    public static final String PT_VENCIMIENTO_PRESENTACION_ANEXO = "0 0 0 6 * ?";
    public static final String CODIGO_OMISION_ANEXO = "PT_SIN_ANEXO";
    public static final String FECHA_ACTUAL = "{FECHA_ACTUAL}";
    public static final String CONTENIDO_NOTIFICACION = "{CUERPO}";
    public static final String DESTINATARIO="{DESTINATARIO}";
    public static final String NIT_CONTRIBUYENTE = "{NIT_CONTRIBUYENTE}";
    public static final String NOMBRE = "{NOMBRE_CONTRIBUYENTE}";
    public static final String DIRECCION = "{DIRECCION_CONTRIBUYENTE}";
    public static final String PERIODO = "{PERIODO_IMPOSICION}";
    public static final String NO_NOTIFICACION = "{NO_AVISO}";
    public static final String NOM_SEQ_CORRELATIVO="IPT_SEQ_NOTIFICACION_ANEXO";
}
