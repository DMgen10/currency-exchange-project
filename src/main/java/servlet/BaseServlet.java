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

    protected boolean isBlank(String field){
        return field == null || field.isBlank();
    }
}
