package servlet;

import exception.BadRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeRateService;

import java.io.IOException;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends BaseServlet {

    private final ExchangeRateService service = new ExchangeRateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            writeJson(resp,200, service.findAll());
        } catch (Exception exception){
            handleException(resp, exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String base = req.getParameter("baseCurrencyCode");
            String target = req.getParameter("targetCurrencyCode");
            String rate = req.getParameter("rate");
            if (isBlank(base) || isBlank(target) || isBlank(rate)){
                throw new BadRequestException("not found currently field form");
            }
            writeJson(resp,201,service.create(base, target, rate));
        } catch (Exception exception){
            handleException(resp, exception);
        }
    }
}
