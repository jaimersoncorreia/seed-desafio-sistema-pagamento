package tech.bacuri.sispay.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FacilitadorJackson {
    /**
     * @param source
     * @return String do objeto serializada para json
     */
    public static String serializa(@NotNull Object source) {
        try {
            return new ObjectMapper().writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param json  json de entrada
     * @param clazz classe do tipo esperado de retorno
     * @param <T>   tipo do retorno
     * @return objeto montado do tipo <T>
     */
    public static <T> T desserializa(@NotBlank String json, @NotNull Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
