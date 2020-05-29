package servlet;

import service.CarService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CarService carService = CarService.getInstance();
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        String price = req.getParameter("price");
        String message;
        if (carService.carAdded(brand, model, licensePlate, price)) {
            message = "Car dealership took the car";
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            message = "Error. The car was not taken!";
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        resp.getWriter().println(message);
    }
}
