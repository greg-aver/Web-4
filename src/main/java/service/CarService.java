package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public boolean buyCar(String brand, String model, String licensePlate) {
        return openSessionCarDAO().buyCar(brand, model, licensePlate);
    }

    public List<Car> getAllCars() {
        return openSessionCarDAO().getAllCars();
    }

    public boolean carAdded(String brand, String model, String licensePlate, String price) {
        return openSessionCarDAO().carAdded(brand, model, licensePlate, price);
    }

    private CarDao openSessionCarDAO() {
        return new CarDao(sessionFactory.openSession());
    }
}
