package DAO;

import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        try {
            List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
            return dailyReports;
        } catch (HibernateException e) {
            e.getStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void newDayCreateLastReport() {
        Transaction transaction = session.beginTransaction();
        try {
            session.saveOrUpdate(new DailyReport(
                    CarDao.earnings,
                    CarDao.soldCars
            ));
            transaction.commit();
            CarDao.newDay();
        } catch (HibernateException e) {
            transaction.rollback();
            e.getStackTrace();
        } finally {
            session.close();
        }
    }

    public DailyReport getLastReport() {
        try {
            /*// Альтернативный вариант
            DailyReport dailyReport = (DailyReport) session.createQuery("from DailyReport order by id DESC").list().get(0);
*/
            DailyReport dailyReport = (DailyReport) session.createQuery("from DailyReport order by id DESC").setMaxResults(1).getSingleResult();
            return dailyReport;
        } catch (HibernateException | ClassCastException e) {
            e.getStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public void deleteAllReports() {
        Transaction transaction = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM DailyReport")
                    .executeUpdate();
            session.createQuery("DELETE FROM Car")
                    .executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            transaction.rollback();
            e.getStackTrace();
        } finally {
            session.close();
        }
    }
}
