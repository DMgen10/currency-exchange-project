package servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import util.Json;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    protected void writeJson(HttpServletResponse response, int status, Object body) throws IOException{
        response.setStatus(status);
        Json.MAPPER.writeValue(response.getWriter(), body);
    }

    protected void handleException(HttpServletResponse response, Exception exception) throws IOException{
        if (exception instanceof ApiException apiException){
            writeJson(response, apiException.getStatus(), new ErrorResponse(apiException.getMessage()));
        } else {
            exception.printStackTrace();
            writeJson(response, 500, new ErrorResponse("Internal server error"));
        }
    }

    protected Map<String, String> parseForm(HttpServletRequest request) throws IOException {
        String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        Map<String,String> result = new HashMap<>();
        for (String pair : body.split("&")){
            if (pair.isBlank()) continue;
            String[] kv = pair.split("=",2);
            result.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8), kv.length > 1 ? URLDecoder.decode(kv[1],StandardCharsets.UTF_8): "");
        }
        return result;
    }

    protected boolean isBlank(String field){
        return field == null || field.isBlank();
    }
}
