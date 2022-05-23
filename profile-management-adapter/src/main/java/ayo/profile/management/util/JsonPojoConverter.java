package ayo.profile.management.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.TimeZone;

/**
 * Created by Aifheli Maganya on 2022/05/18.
 */
public class JsonPojoConverter {

    private static ObjectMapper mapper;

    static{
        mapper= new ObjectMapper();
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonPojoConverter() {
    }

    public static <T> T convertJsonToObject(String json, Class<T> convertingClass) {
        T cass = null;
        try {
            cass = mapper.readValue(json, convertingClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cass;
    }

    public static <T> T convertJsonToObject(String json, TypeReference<T> typeReference) {
        T cass = null;
        try {
            cass = mapper.readValue(json, typeReference);
        } catch (IOException e) {
            ApplicationLoggerUtil.error(JsonPojoConverter.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    EventLogUtil.LOGGER_EVENT.CONVERT_JSON_EVENT.toString(), e);
            throw new RuntimeException(e);
        }
        return cass;
    }

    public static <T> T convertJsonFileToObject(File json, Class<T> convertingClass) throws IOException {
        T cass = null;
        try {
            cass = mapper.readValue(json, convertingClass);
        } catch (IOException e) {
            ApplicationLoggerUtil.error(JsonPojoConverter.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    EventLogUtil.LOGGER_EVENT.CONVERT_JSON_EVENT.toString(), e);
            throw e;
        }
        return cass;
    }

    public static String convertObjectToJson(Object clas) {
        return convertObjectToJson(clas, false);
    }

    public static String convertObjectToJson(Object clas, boolean pretty) {
        String response = null;
        try {
            response = pretty ? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(clas) : mapper.writeValueAsString(clas);
        } catch (IOException e) {
            ApplicationLoggerUtil.error(JsonPojoConverter.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    EventLogUtil.LOGGER_EVENT.CONVERT_JSON_EVENT.toString(), e);
            try {
                throw e;
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        }
        return response;
    }


}
