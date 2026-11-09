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
}
