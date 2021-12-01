package com.kalugin.net.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalugin.net.service.HttpWeatherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "weatherServlet", urlPatterns = "/weather")
public class WeatherServlet extends HttpServlet {
    private final HttpWeatherService weatherService = new HttpWeatherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("weather.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        Map<String, String> result = weatherService.get(city);
        String json = new ObjectMapper().writeValueAsString(result);

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        if(result == null) {
            resp.getWriter().write("This city is not found!");
        } else {
            resp.getWriter().write(json);
        }
    }
}
