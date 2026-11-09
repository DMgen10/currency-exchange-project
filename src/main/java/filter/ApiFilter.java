package filter;

import jakarta.servlet.*;

import java.io.IOException;

@WebFilter({"/currencies", "/currency/*","/exchangeRates","/exchangeRate/*","/exchange"})
public class ApiFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
