/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.mineducacion.seguridad.rest.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author jcardona
 */
@Provider
//@Component
public class JerseyObjectMapperProvider implements ContextResolver<ObjectMapper> {

    @Override
    public ObjectMapper getContext(Class<?> type) {
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.setDateFormat(new SimpleDateFormat() {
            @Override
            public Date parse(String source) throws ParseException {
                if (source.endsWith("Z")) {
                    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'").parse(source);
                } else {
                    return new SimpleDateFormat("dd/MM/yyyy").parse(source);
                }
            }

            @Override
            public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
                return new StringBuffer(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'").format(date));
            }

        });
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        return mapper;
    }

    private static void descativarLog() {
        for (String l
                : Collections.list(LogManager.getLogManager().getLoggerNames())) {
            if (l.startsWith("com.sun.jersey")) {
                Logger.getLogger(l).setLevel(Level.OFF);
            }
        }
    }
    static {
        descativarLog();
    }
}
