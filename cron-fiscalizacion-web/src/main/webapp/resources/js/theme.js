/**
 * Esta funcion se encarga de redimensionar el frame principal, en todo caso exista
 * Se encuentra ubicada onload de la pagina por lo que tambien llama al reloj
 */
function onreload(){
    reloj();
    
    // esto posiblemente no se ejecute por cuestiones de seguridad
    var iframe = window.parent.document.all.desplegar; 
    if(typeof iframe !== 'undefined'){
        iframe.height= screen.availHeight; 
    } 
}

/**
 * Esta funcion se encarga de mostrar un reloj, este se actualiza cada segundo 
 */
function reloj() {
    var fechaActual = new Date();

    var dia = fechaActual.getDate();
    var mes = fechaActual.getMonth() + 1;
    var anio = fechaActual.getFullYear();
    var hora = fechaActual.getHours();
    var minuto = fechaActual.getMinutes();
    var segundo = fechaActual.getSeconds();

    dia = agregarCero(dia);
    mes = agregarCero(mes);
    hora = agregarCero(hora);
    minuto = agregarCero(minuto);
    segundo = agregarCero(segundo);
    
    //document.getElementById('fecha').innerHTML = dia + "/" + mes + "/" + anio + " " + hora + ":" + minuto + ":" + segundo;
    if (document.getElementById('fechaIngreso')) {
        document.getElementById('fechaIngreso').innerHTML = dia + "/" + mes + "/" + anio + " " + hora + ":" + minuto + ":" + segundo;
    }
    
    if (document.getElementById('fechaFiniquito')) {
        document.getElementById('fechaFiniquito').innerHTML = dia + "/" + mes + "/" + anio + " " + hora + ":" + minuto + ":" + segundo;
    }   
   
    if (document.getElementById('fechaDevol')) {
        document.getElementById('fechaDevol').innerHTML = dia + "/" + mes + "/" + anio + " " + hora + ":" + minuto + ":" + segundo;
    }
    
    if (document.getElementById('fechaMemo')) {
        document.getElementById('fechaMemo').innerHTML = dia + "/" + mes + "/" + anio + " " + hora + ":" + minuto + ":" + segundo;
    }
    
    if (document.getElementById('fechaAux')) {
        document.getElementById('fechaAux').innerHTML = dia + "/" + mes + "/" + anio + " " + hora + ":" + minuto + ":" + segundo;
    }
    if (document.getElementById('fechaHeader')) {
        document.getElementById('fechaHeader').innerHTML = dia + "/" + mes + "/" + anio + " " + hora + ":" + minuto + ":" + segundo;
    }
    
    t = setTimeout(function() {
        reloj()
    }, 500);
}

/**
 * Esta funcion agrega un cero a la izquieda en numeros menores a 10
 */
function agregarCero(valor) {
    if(valor < 10) {
        valor = "0" + valor;
    }
    return valor;
}

/**
 * Esta funcion permite transforma una fecha (formato de milisegundos) a una
 * cadena con el formato dd/MM/yyyy.
 **/
function transformarFecha(fechaMilisegundos) {
    var fecha = new Date();
    fecha.setTime(fechaMilisegundos);
    var dia = agregarCero(fecha.getDate());
    var mes = agregarCero(fecha.getMonth() + 1);
    var anio = fecha.getFullYear();
    return (dia  + '/' + mes + '/' + anio);
}

/**
 * Esta funcion permite limpiar el formulario de la pagina de consultas, esto
 * se hace con javascript debido a la fase de validaciones de JSF, ya que si 
 * alguno de los campos no cumple con alguna validacion la fase de validacion
 * lanza el error y ya no permite la llamada a las demas faces por lo que el
 * formulario ya no se limpia, por ese motivo primero se limpia el formulario
 * con javascript.
 */
function limpiarFrmConsulta() {
//    console.log("Limpiando el formulario de consultas");
//    var fechaActual = (new Date()).getTime();
//    var dia = 24 * 60 * 60 * 1000;
    
    document.getElementById('frmPrincipal:fechaInicioInputDate').value = '';
    document.getElementById('frmPrincipal:fechaFinInputDate').value = '';
//    document.getElementById('frmPrincipal:fechaInicioInputDate').value = transformarFecha((fechaActual - dia));
//    document.getElementById('frmPrincipal:fechaFinInputDate').value = transformarFecha(fechaActual);
    
    if(document.getElementById('frmPrincipal:codigoAduana') !== null) {
        document.getElementById('frmPrincipal:codigoAduana').value = '';
    } else {
        document.getElementById('frmPrincipal:codigoAduana2').value = '';
    }
}

/**
 * Esta funcion permite limpiar el formulario de la pagina de reasignacion de
 * gestiones, esto se hace con javascript debido a la fase de validaciones de
 * JSF, ya que si alguno de los campos no cumple con alguna validacion la fase 
 * de validacion lanza el error y ya no permite la llamada a las demas faces por
 * lo que el formulario ya no se limpia, por ese motivo primero se limpia el 
 * formulario con javascript.
 */
function limpiarFrmConsultaCuscar() {
//    var fechaActual = (new Date()).getTime();
//    var dia = 24 * 60 * 60 * 1000;
    document.getElementById('frmPrincipal:numeroArchivo').value = '';
    document.getElementById('frmPrincipal:numeroManifiesto').value = '';
//    document.getElementById('frmPrincipal:fechaInicioInputDate').value = transformarFecha((fechaActual - dia));
//    document.getElementById('frmPrincipal:fechaFinInputDate').value = transformarFecha(fechaActual);
}

/**
 * Esta funcion permite limitar la cantidad de caracteres que se pueden ingresar
 * en un TextArea, se crea esta funcion porque ese componente no tiene esa
 * funcionalidad en versiones menores a HTML 5.
 * @param {string} elemento
 * @param {string} limite
 */
function limitarTextArea(elemento, limite) {
    if(elemento.value.length > limite) {
        elemento.value = elemento.value.substring(0, limite);
    }
}

function parseIntegers(field)
{   
    var currency = /^\d{0,10}(?:\d{0})?$/;
    var onlyCurrency = /^(\d{0,10}(?:\d{0})?)[\s\S]*$/;

    if(!currency.test(field.value))
    {
        field.value = field.value.replace(onlyCurrency, "$1");
    }
}

function validarCamposNumericos(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla===8) return true;
    patron =/^[0-9]$/;
    te = String.fromCharCode(tecla);
    return patron.test(te);      
}

function validarCamposAlfaNumericos(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla===8 || tecla===13) return true;
    patron =/^[A-z0-9]*$/;
    //patron =/^\d*\.?\d*$/;
    return patron.test(String.fromCharCode(tecla));
}

function validarCamposString(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla===8 || tecla===13) return true;
    patron =/^[A-záéíóú\s]*$/;
    //patron =/^\d*\.?\d*$/;
    return patron.test(String.fromCharCode(tecla));
}

function validarCamposTextArea(e) {
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla===8 || tecla===13) return true;
    patron =/^[A-z0-9ñÑ.,áéíóú\s]*$/;
    //patron =/^\d*\.?\d*$/;
    return patron.test(String.fromCharCode(tecla));
}

function obtenerNameField(field1,field2,pos){
    var parents = field1.split(":");
    var fieldHelp="";
    for(i=0; i<(parents.length-1); i++) {
        if(i!==0){
            fieldHelp=fieldHelp+":"+parents[i];
        } else {
            fieldHelp=parents[i];
        }
        
        if(i===(parents.length-parseInt(pos))){
            field2 = fieldHelp+":"+field2;
        }
    }
    return field2;
}

function buscarPersona(field1, field2, field3, frm) {
    if(frm===undefined){
        frm = "buscarDatosPersona";
    }
    if (document.getElementById(frm)) {
        field2 = obtenerNameField(field1, field2, 2);
        document.getElementById(frm + ':field1').value = field1;
        document.getElementById(frm + ':field2').value = field2;
        document.getElementById(frm + ':field3').value = field3;
        if (document.getElementById(frm + ':txtNitPersona')) {
            document.getElementById(frm + ':txtNitPersona').focus();
        }
    }
}

function mostrarPersona(fd1,txt2,txt3, pos){
    var row = 2;
    txt2 = obtenerNameField(fd1,txt2, row);
    txt3 = obtenerNameField(fd1,txt3, row);
    var f1 = obtenerNameField(fd1,'field1', pos);
    var f2 = obtenerNameField(fd1,'field2', pos);
    var f3 = obtenerNameField(fd1,'field3', pos);
    var btn = obtenerNameField(fd1,'btnCancelar', pos);
    var field1 = document.getElementById(f1).value;
    var field2 = document.getElementById(f2).value;
    var field3 = document.getElementById(f3).value;
    var val1 = document.getElementById(txt2).value;
    var val2 = document.getElementById(txt3).value;
    var cmp1 = document.getElementById(field1);
    var cmp2 = document.getElementById(field2);
    var nameTool = (field1+"Tooltip:content");
    if(document.getElementById(nameTool)) {
        var tool = document.getElementById(nameTool);
        tool.innerHTML = val2+"<br>"+val1;
    }
    if (document.getElementById(btn)) {
        document.getElementById(btn).click();
    }
    cmp2.value = val2; 
    cmp1.value = val1;
    
    if(field3 !== ""){
        if(field3 === "datosResponsable"){
            datosResponsable(val2);
        } else if(field3 === "datosPiloto"){
            datosPiloto(val2);
        }
    }
}

//Contador de Caracteres
function contadorCaracteres(pTextArea, pRestante, pLimite) {
    var textArea = document.getElementById(pTextArea);
    var field2 = obtenerNameField(pTextArea, pRestante, 2);   
    var restantes = document.getElementById(field2);
    if (document.getElementById(field2)) {        
        if (textArea.value.length > pLimite) {
            textArea.value = textArea.value.substring(0, pLimite);
        } else {
            restantes.value = "Caracteres Restantes: " + (pLimite - textArea.value.length);
        }
    }
}
