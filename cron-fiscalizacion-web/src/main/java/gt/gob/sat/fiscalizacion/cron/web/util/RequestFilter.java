/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.gob.sat.fiscalizacion.cron.web.util;

import gt.gob.sat.fiscalizacion.fisat.dto.UsuarioDto;
import gt.gob.sat.fiscalizacion.fisat.srv.GeneralSrv;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author rabaraho
 */
public class RequestFilter implements Filter {

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
  
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RequestFilter.class);
//______________________________________________________________________________
    /**
     * <p>
     * Servicio gestionado por Spring, contiene la logica del negocio.</p>
     */
    private GeneralSrv generalSrvImpl;
//______________________________________________________________________________
    /**
     * <p>
     * Permite saber la informacion del cliente web que se utiliza.</p>
     */
    private UserAgentStringParser uasParser;
//______________________________________________________________________________

    /**
     * <p>
     * Metodo de configuracion del filtro, se ejecuta al momento de asignarse
     * memoria al objeto.</p>
     *
     * @param pFilterConfig Objeto con los parametros de configuracion del
     * filtro
     * @throws ServletException Si ocurre alguna excepcion al iniciar el filtro
     */
    @Override
    public void init(FilterConfig pFilterConfig) throws ServletException {
        LOG.error("init: RequestFilter");
        if (this.generalSrvImpl == null) {
            ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(pFilterConfig.getServletContext());
            this.generalSrvImpl = appCtx.getBean(GeneralSrv.class);
        }
        if (this.uasParser == null) {
            try {
                this.uasParser = UADetectorServiceFactory.getResourceModuleParser();
            } catch (Throwable e) {
                LOG.error("Error al cargar el UserAgentStringParser", e);
            }
        }
    }
//______________________________________________________________________________

    /**
     * <p>
     * Metodo principal del filtro, se ejecuta cada vez que se realiza una
     * peticion a cualquiera de los recursos que el filtro monitorea, el metodo
     * se encarga de verificar el perfil del usuario y si este esta utilizando
     * un cliente web compatible. La verificacion del perfil del usuario se hace
     * aqui debido a que la libreria de seguridad {@code securityAgent} no
     * elimina las sesiones al menos que se cierre el explorador, por lo tanto
     * si alguien cierra sesion y luego inicia sesion con otro usuario se
     * reutiliza la misma sesion. Por lo tanto es necesario, con cada peticion,
     * verificar si el login del usuario sigue siendo el mismo, si este cambio
     * se actualiza el perfil del usuario y asi evitar mostrar informacion que
     * no corresponda.</p>
     *
     * @param pRequest Peticion hecha por el usuario
     * @param pResponse Respuesta para el usuario
     * @param pChain Cadena que contiene todos los filtros que aplican a la
     * peticion
     * @throws IOException Si ocurre algun error de lectura-escritura
     * @throws ServletException Si ocurre algun error con el contenedor
     */
    @Override
    public void doFilter(ServletRequest pRequest, ServletResponse pResponse,
            FilterChain pChain) throws IOException, ServletException {

        this.leerUserAgent(pRequest);

        try {

            if (this.verificarUsuarioSesion(pRequest)) {
                pChain.doFilter(pRequest, pResponse);
            } else {
                ((HttpServletResponse) pResponse).sendError(403);
            }

        } catch (Throwable e) {
            LOG.error("Error desconocido al procesar la peticion " + e.getMessage(), e);
            ((HttpServletResponse) pResponse).sendError(500);
        }
    }
//______________________________________________________________________________

    /**
     * <p>
     * Metodo de liberacion de recursos, se invoca al momento de eliminar el
     * objeto de memoria.</p>
     */
    @Override
    public void destroy() {
        LOG.debug("destroy: RequestFilter");
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este metodo se encarga de obtener el login del usuario que esta haciendo
     * las peticiones, en base a este login el metodo completa toda la
     * informacion del perfil del usuario y crea un bean el cual es insertado en
     * la sesion.</p>
     *
     * @param pRequest Peticion hecha por el cliente
     * @return true Si, y solo si, se cargo toda la informacion del usuario
     * @throws Exception Si ocurre un error al obtener la informacion del
     * usuario
     */
    private boolean verificarUsuarioSesion(ServletRequest pRequest) throws Exception {
        // se obtiene el login del usuario que realiza la peticion       
        HttpSession session = ((HttpServletRequest) pRequest).getSession();

        String loginTitular = (String) session.getAttribute("SAT_SEGURIDAD_login");

        String loginDelegado = (String) session.getAttribute("SAT_SEGURIDAD_loginColaborador");

        String login = "";
        boolean actualizar = false;

        // se verifica si el ingreso es de un delegado
        if (StringUtils.isNotBlank(loginDelegado)) {
            login = loginDelegado;

        } else if (StringUtils.isNotBlank(loginTitular)) {
            login = loginTitular;

        }

        UsuarioDto usuarioDto = null;
        if (StringUtils.isNotBlank(login)) {
            // se verifica si ya existe un perfil para el login
            usuarioDto = (UsuarioDto) session.getAttribute("SAT_USUARIO_COMPLETO");

            if ((usuarioDto == null) || (!usuarioDto.getUsuario().equalsIgnoreCase(login))) {

                actualizar = true;
            }
        }

        // si es necesario se vuelve a carga la informacion del usuario
        if (actualizar) {
            if (!"".equals(login)) {
                usuarioDto = new UsuarioDto();
                usuarioDto.setUsuario(login.trim());
            }
            session.setAttribute("SAT_USUARIO_COMPLETO", usuarioDto);
            return true;
        } else {
            return true;
        }
    }
//______________________________________________________________________________

    /**
     * <p>
     * Este metodo se encarga de interpretar la cabecera de USER-AGENT para
     * crear un bean con la informacion del cliente utilizado para acceder a la
     * aplicacion.</p>
     *
     * @param pRequest Peticion hecha por el cliente
     */
    private void leerUserAgent(ServletRequest pRequest) {

        HttpSession session = ((HttpServletRequest) pRequest).getSession();

        // ReadableUserAgent agent = (ReadableUserAgent) session.getAttribute(Constante.ATTR_BEAN_USER_AGENT);        
        ReadableUserAgent agent = (ReadableUserAgent) session.getAttribute("SAT_USER_AGENT");

        if (agent == null) {

            String userAgent = ((HttpServletRequest) pRequest).getHeader("user-agent");

            try {
                agent = this.uasParser.parse(userAgent);

                //session.setAttribute(Constante.ATTR_BEAN_USER_AGENT, agent);        
                session.setAttribute("SAT_USER_AGENT", agent);

            } catch (Throwable e) {

                LOG.error("Error al parsear el USER-AGENT", e);
            }
        }
    }

}
