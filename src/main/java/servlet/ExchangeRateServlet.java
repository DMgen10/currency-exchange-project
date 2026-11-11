package servlet;


@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {
    private final ExchangeRateService service = new ExchangeRateService();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("PATCH".equalsIgnoreCase(req.getMethod())){
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }
}
