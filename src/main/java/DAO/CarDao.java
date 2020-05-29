package DAO;

import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.regex.Pattern;

public class CarDao {

    final private static int maxCountOfCars = 10;
    public static long earnings;
    public static long soldCars;

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public static void newDay() {
        earnings = 0;
        soldCars = 0;
    }

    public boolean updateData(String brand, String model, String licensePlate) throws HibernateException, NoResultException {
        try {
            String hqlRequest = "SELECT c.price FROM Car c WHERE c.brand = :brand and c.model = :model " +
                    "and c.licensePlate = :licensePlate";
            Query query = session.createQuery(hqlRequest)
                    .setString("model", model)
                    .setString("brand", brand)
                    .setString("licensePlate", licensePlate);
            long price = (long) query.getSingleResult();
            earnings += price;
            soldCars++;
            return true;
        } catch (HibernateException | NullPointerException | NoResultException e) {
            e.getStackTrace();
            return false;
        }
    }

    public boolean deleteCar(String brand, String model, String licensePlate) throws HibernateException {
        Transaction transaction = session.beginTransaction();
        try {
            String hqlRequest = "DELETE FROM Car c WHERE c.brand = :brand and c.model = :model " +
                    "and c.licensePlate = :licensePlate ";
            Query query = session.createQuery(hqlRequest);
            query.setString("model", model);
            query.setString("brand", brand);
            query.setString("licensePlate", licensePlate);
            query.executeUpdate();
            transaction.commit();
            return true;
        } catch (HibernateException | NullPointerException | NoResultException e) {
            e.getStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public boolean buyCar(String brand, String model, String licensePlate) {
        boolean result = updateData(brand, model, licensePlate) && deleteCar(brand, model, licensePlate);
        session.close();
        return result;
    }


    public boolean isNumberCarsExceeded(String brand) throws HibernateException {
        String hqlRequest = "from Car c where c.brand = :brand";
        int result = session.createQuery(hqlRequest)
                .setString("brand", brand)
                .list()
                .size();
        if (result >= maxCountOfCars) {
            return true;
        }
        return false;
    }


    public List<Car> getAllCars() {
        try {
            List<Car> listOfCars = session.createQuery("FROM Car").list();
            return listOfCars;
        } catch (HibernateException e) {
            e.getStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean carAdded(String brand, String model, String licensePlate, String priceString) {
        Transaction transaction = session.beginTransaction();
        try {
            if (isNumberNotInteger(priceString) || isNumberCarsExceeded(brand)) {
                return false;
            }
            long price = Long.parseLong(priceString);
            session.save(new Car(
                    brand,
                    model,
                    licensePlate,
                    price
            ));
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            e.getStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean isNumberNotInteger(String string) {
        try {
            final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");
            return !INTEGER_PATTERN.matcher(string).matches();
        } catch (NullPointerException e) {
            return true;
        }
    }
}
