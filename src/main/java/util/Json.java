package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    public static final ObjectMapper MAPPER = new ObjectMapper().enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

    private Json(){}
}
