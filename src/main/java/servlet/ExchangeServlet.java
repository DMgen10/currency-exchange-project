package servlet;

import exception.BadRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeService;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends BaseServlet{

    private final ExchangeService service = new ExchangeService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String from = req.getParameter("from");
            String to = req.getParameter("to");
            String amount = req.getParameter("amount");
            if (isBlank(from) || isBlank(to) || isBlank(amount)){
                throw new BadRequestException("no required parameter is specified: from, to, or amount");
            }
            writeJson(resp,200,service.exchange(from,to,amount));
        } catch (Exception exception){
            handleException(resp, exception);
        }
    }
}
