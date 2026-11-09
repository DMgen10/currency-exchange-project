package servlet;

import exception.BadRequestException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends BaseServlet {

    private final CurrencyService service = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String path = req.getPathInfo();
            if (path == null || path.length() <= 1){
                throw new BadRequestException("the currency code is missing from the address");
            }
            writeJson(resp, 200, service.findByCode(path.substring(1)));
        } catch (Exception exception){
            handleException(resp, exception);
        }
    }
}
