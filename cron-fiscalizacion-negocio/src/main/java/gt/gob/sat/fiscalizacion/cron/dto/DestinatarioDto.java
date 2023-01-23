/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.dto;

import java.util.List;

/**
 *
 * @author emalvarb
 */
public class DestinatarioDto {
    private List<IncumplimientoAnexoISRDto> datosContribuyentes;
    private List<IncumplimientoAnexoISRDto> correosEnBancos;
    private List<IncumplimientoAnexoISRDto> correosEnSatEnLinea;

    /**
     * @return the datosContribuyentes
     */
    public List<IncumplimientoAnexoISRDto> getDatosContribuyentes() {
        return datosContribuyentes;
    }

    /**
     * @param pDatosContribuyentes the datosContribuyentes to set
     */
    public void setDatosContribuyentes(List<IncumplimientoAnexoISRDto> pDatosContribuyentes) {
        this.datosContribuyentes = pDatosContribuyentes;
    }

    /**
     * @return the correosEnBancos
     */
    public List<IncumplimientoAnexoISRDto> getCorreosEnBancos() {
        return correosEnBancos;
    }

    /**
     * @param pCorreosEnBancos the correosEnBancos to set
     */
    public void setCorreosEnBancos(List<IncumplimientoAnexoISRDto> pCorreosEnBancos) {
        this.correosEnBancos = pCorreosEnBancos;
    }

    /**
     * @return the correosEnSatEnLinea
     */
    public List<IncumplimientoAnexoISRDto> getCorreosEnSatEnLinea() {
        return correosEnSatEnLinea;
    }

    /**
     * @param pCorreosEnSatEnLinea the correosEnSatEnLinea to set
     */
    public void setCorreosEnSatEnLinea(List<IncumplimientoAnexoISRDto> pCorreosEnSatEnLinea) {
        this.correosEnSatEnLinea = pCorreosEnSatEnLinea;
    }
}
