package servlet;

import exception.BadRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeRateService;
import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {
    private final ExchangeRateService service = new ExchangeRateService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            writeJson(resp,200,service.findByCodes(extractPair(req)));
        } catch (Exception exception){
            handleException(resp, exception);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String rate = parseForm(req).get("rate");
            if (isBlank(rate)){
                throw new BadRequestException("not found currently field form");
            }
            writeJson(resp,200, service.updateRate(extractPair(req), rate));
        } catch (Exception exception){
            handleException(resp, exception);
        }
    }

    private String extractPair(HttpServletRequest req) {
        String path = req.getPathInfo();
        if (path == null || path.length() <= 1){
            throw new BadRequestException("codes currencies not found in address");
        }
        return path.substring(1);
    }
}
