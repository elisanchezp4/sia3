/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.heinsohn.men.parametros.rest.config;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 * @author jcardona
 */
@Provider
@Component
public class JerseyObjectMapperProvider implements ContextResolver<ObjectMapper> {

    @Override
    public ObjectMapper getContext(Class<?> type) {
        
        ObjectMapper result = new ObjectMapper();
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return result;
    }
}
