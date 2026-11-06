package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        try(Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery("SELECT Code, FullName FROM Currencies")){
            while (resultSet.next()){
                resp.getWriter().println(resultSet.getString("Code") + " - " + resultSet.getString("FullName"));
            }
        } catch (SQLException exception){
            throw new IOException(exception);
        }
    }
}
