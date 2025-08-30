/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.mineducacion.seguridad.web.servicio.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 * Clase encargada de identificar y convertir listas de String a su correspondiente representación
 * como un arreglo JSON para el envío por medio de los servicios REST. Este proveedor
 * es automáticamente llamado por el framework cuando en un servicio se devuelve una lista 
 * de Strings.
 * 
 * @author jsoto
 */
@Provider 
@Produces({APPLICATION_JSON})
public class ListaStringMessageBodyWriter implements MessageBodyWriter<List<String>>{
 
    /**
     * Identifica si lo que está retornando el servicio es del tipo List<String>.
     * 
     * @param type {@inheritDoc}
     * @param genericType {@inheritDoc}
     * @param annotations {@inheritDoc}
     * @param mediaType {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {   
        List<Class<?>> interfaces = Arrays.asList(type.getInterfaces());        
        boolean isList = interfaces.contains(List.class);
        boolean isGenericString = genericType.toString().contains(String.class.getName());
        
        return isList && isGenericString ;
    }
 
    /**
     * Deprecated by JAX-RS 2.0 and ignored by Jersey runtime
     * 
     * @param lista  {@inheritDoc}
     * @param type  {@inheritDoc}
     * @param genericType  {@inheritDoc}
     * @param annotations  {@inheritDoc}
     * @param mediaType  {@inheritDoc}
     * @return 
     */
    @Override
    public long getSize(List<String> lista, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return 0;
    }

    /**
     * Recibe la lista de Strings y las trasnforma en su correspondiente
     * representación como un arreglo JSON.
     * 
     * @param lista {@inheritDoc}
     * @param type {@inheritDoc}
     * @param genericType {@inheritDoc}
     * @param annotations {@inheritDoc}
     * @param mediaType {@inheritDoc}
     * @param httpHeaders {@inheritDoc}
     * @param entityStream {@inheritDoc}
     * @throws IOException {@inheritDoc}
     * @throws WebApplicationException {@inheritDoc}
     */
    @Override
    public void writeTo(List<String> lista,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream)
                        throws IOException {
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i<lista.size(); i++){
            sb.append(lista.get(i));
            if(i<lista.size()-1){
                sb.append(", ");
            }
        }
        sb.append("]");
        
        DataOutputStream dos = new DataOutputStream(entityStream);

        dos.writeUTF(sb.toString());
 
    }
}
