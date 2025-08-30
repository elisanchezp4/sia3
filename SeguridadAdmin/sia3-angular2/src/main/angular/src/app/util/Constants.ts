export var URL_BASE: string = location.protocol + '//' + location.hostname + (location.port ? ':' + location.port : '') + "/sia3-apiRest/rest/servicios";
export var URL_BASE_PARAMETROS: string = "rest/AdministrarParametro";
//export var URL_BASE: string = location.protocol + '//' + location.hostname + ":7001/sia3-apiRest/rest/servicios";
//export var URL_BASE_PARAMETROS: string = location.protocol + '//' + location.hostname + ":7001/parametros-apiRest/rest/AdministrarParametro";
export var RESPONSE_OK: number = 200;
export var SEPARADOR_FORMATO_DD_MM_ANIO: string = "/";
export var SEPARADOR_FORMATO_DD_MM_ANIO_2: string = "-";
export var FORMATO_FECHA: string = "dd/MM/yy";
export var EXP_REGULAR_EMAIL: string = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
export var ERROR_EN_PETICION: string = "Ha ocurrido un error inesperado al realizar la petición";
export var ESTADO_ACTIVO: string = "1";
export var ESTADO_INACTIVO: string = "0";
//APLICACION
export var ERROR_VALIDACION_CAMPOS_APLICACION: string = "Campos obligatorios pendientes por diligenciar";
export var ERROR_VALIDACION_CAMPO_DESCRIPCION: string = "Debe ingresar la descripción de la aplicación";
export var ERROR_VALIDACION_CAMPO_URLEXITOSA: string = "Debe ingresar la URL de inicio exitoso";
// export var ERROR_VALIDACION_CAMPO_URLFALLIDA: string = "Debe ingresar la URL de inicio fallido";
export var ERROR_VALIDACION_CAMPO_MINUTOCODIGO: string = "Debe ingresar los minutos de vigencia del código de la aplicación";
export var ERROR_VALIDACION_CAMPO_MINUTOTOKEN: string = "Debe ingresar los minutos de vigencia del token de la aplicación";
export var ERROR_VALIDACION_CAMPO_MINUTOCODIGO_NUMERO: string = "El campo minutos vigencia del código de la aplicación debe ser numérico";
export var ERROR_VALIDACION_CAMPO_MINUTOTOKEN_NUMERO: string = "El campo minutos vigencia del token de la aplicación debe ser numérico";
export var CONFIRMACION_INACTIVAR_APLICACION: string = "¿Está seguro de que desea eliminar la aplicación?";
export var ERROR_VALIDACION_CAMPO_ESTADO: string = "Debe seleccionar un estado";
export var CARGANDO: string = "CARGANDO";//Cargando...
export var DUPLICADOS: string = "DUPLICADOS";
//ROL
export var ERROR_VALIDACION_CAMPO_NOMBRE_ROL: string = "Debe ingresar el nombre del rol";
export var ERROR_VALIDACION_CAMPO_ESTADO_ROL: string = "Debe seleccionar el estado del rol";
export var ERROR_VALIDACION_CAMPOS_ROL: string = "Campos obligatorios pendientes por diligenciar";
export var CONFIRMACION_INACTIVAR_ROL: string = "¿Está seguro de que desea eliminar el rol?";
//OPERACION
export var ERROR_VALIDACION_CAMPOS_OPERACION: string = "Campos obligatorios pendientes por diligenciar";
export var ERROR_VALIDACION_CAMPO_ORDEN_VISUALIZACION: string = "El campo orden de visualización no puede ser mayor a 999";
export var ERROR_VALIDACION_CAMPO_DESCRIPCION_OPERACION: string = "Debe ingresar la descripción de la operación";
export var ERROR_VALIDACION_CAMPO_ESTADO_OPERACION: string = "Debe seleccionar el estado de la operación";
export var ERROR_VALIDACION_CAMPO_APLICACION_OPERACION: string = "Debe seleccionar la operación";
export var CONFIRMACION_INACTIVAR_OPERACION: string = "¿Está seguro de que desea eliminar la operación?, se eliminarán todas las sub-operaciones a cargo.";
export var CU007: string = '¿Seguro que quiere cancelar la operación?';

//USUARIO
export var CU003MEN01: string = "CU003MEN01";//falta campos obligatorios
export var CU003MEN02: string = "CU003MEN02";//Ocurrió error accediendo a la información del Sistema
export var CU003MEN05: string = "CU003MEN05";//Usuario inactivado exitosamente
export var CU003MEN08: string = "CU003MEN08";//Usuario activado exitosamente
export var CU003MEN10: string = "CU003MEN10";//¿Desea inactivar el usuario?
export var CU003MEN11: string = "CU003MEN11";//¿Seguro que quiere cancelar el proceso?
export var CU003MEN28: string = "CU003MEN28";//Usuario no existe en el sistema
export var CU003MEN42: string = "CU003MEN42";//¿Desea activar el usuario?

//USUARIO/ROL
export var CU008MEN17: string = "CU008MEN17";//falta campos obligatorios

//AUDITORIA
export var APP100114: string = "APP100114";//falta campos obligatorios auditoria


//USUARIO/APLICACION
export var APP100113: string = "APP100113";//Debe seleccionar por lo mennos un usuario
export var APP100115: string = "APP100115";//No existen aplicaciones asociadas al usuario

//CONSULTA CATALOGOS
export var CATALOGO_ESTADO: string = "ESTADO";
export var CATALOGO_TIPO_EVENTO: string = "TIPO EVENTO";
export var CATALOGO_TIPO_USUARIO: string = "TIPO USUARIO";

//LISTA DE VALORES PARAMETROS
export var UNIVALOR: string = "Univalores";
export var MULTIVALOR: string = "Multivalores";
export var DEPENDIENTE: string = "Dependiente";

//MENSAJES PARA APP DE PARAMETROS
export var PARAMETROSAPP01: string = "PARAMETROSAPP01";//Parametro modificado correctamente
export var PARAMETROSAPP02: string = "PARAMETROSAPP02";//Parametro agregado correctamente
export var PARAMETROSAPP03: string = "PARAMETROSAPP03";//No se encontraron registros para los valores ingresados
export var PARAMETROSAPP04: string = "La aplicación buscada no existe";

// Seguridad SDK
//export var URL_BASE_SEGURIDAD: string = "http://192.168.32.83:7003/seguridad-apiRest/recursoswebseguridad/servicios/autenticacion";
export var TOKEN: string = "access_token";
export var USER_ID: string = "user_id";
//export var CLIENT_ID: number = 35;
export var APPSIA3: string = "35";
export var APLICACION_ID: string = "id_Aplicacion";
export var URL_PARAMETROS: string = "url_parametros";


export var MENU: string = "MENU";
export var VINCULO: string = "VINCULO";
export var LI_MENU_ABRE: string = "<li id=\"opc";
export var LI_MENU_ABRE2: string = "\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">";
export var LI_MENU_CIERRA: string = "</li>";
export var LI_VINCULO_ABRE: string = "<li class=\"dropdown\" id=\"opc";
export var LI_VINCULO_ABRE2: string = "\"><a routerLink=\"";
export var LI_VINCULO_MEDIO: string = "\" href=\"#";
export var LI_VINCULO_MEDIO2: string = "\" ><span class=\"xcaret\"></span>";
export var LI_VINCULO_CIERRA: string = "</a></li>";
export var UL_ABRE: string = "<ul class=\"dropdown-menu\">";
export var UL_CIERRA: string = "</ul>";
export var A_CIERRA: string = "</a>";






