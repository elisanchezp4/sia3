package co.gov.mineducacion.seguridad.modelo.manejadores.utils;

import co.gov.mineducacion.seguridad.modelo.enums.FuncionAgrupamientoJPQL;

/**
 * Almacena la información de agrupamiento (COUNT, DISTINCT, etc)
 * para las consultas SELECT JPQL.
 * @author jsoto
 */
public class InformacionAgrupamiento {
    
    private String atributo;
    private FuncionAgrupamientoJPQL funcionAgrupamiento;

    /**
     * Constructor para cuando la funcion de agrupamiento es de tipo DISTINCT, en
     * cuyo caso se debe especificar un nombre de atributo.
     * @param funcionAgrupamiento Función de agrupamiento a utilizar en la consulta.
     * @param atributo Atributo sobre el cual se ejecuta el DISTINCT
     */
    public InformacionAgrupamiento(FuncionAgrupamientoJPQL funcionAgrupamiento,
                String atributo){
        this.funcionAgrupamiento = funcionAgrupamiento;
        this.atributo = atributo;
    }

    /**
     * Constructor para cuando la función de agrupamiento es de tipo COUNT o NINGUNA.
     * En este caso no se especifica un nombre de atributo.
     * @param funcionAgrupamiento Función de agrupamiento a utilizar en la consulta.
     */
    public InformacionAgrupamiento(FuncionAgrupamientoJPQL funcionAgrupamiento) {
        this.funcionAgrupamiento = funcionAgrupamiento;
        this.atributo = "";
    }            

    public String getAtributo() {
        return atributo;
    }

    public FuncionAgrupamientoJPQL getFuncionAgrupamiento() {
        return funcionAgrupamiento;
    }
    
    
    
}
