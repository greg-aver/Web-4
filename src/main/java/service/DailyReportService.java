package service;

import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public void newDay() {
        openSessionReportDAO().newDayCreateLastReport();
    }

    public List<DailyReport> getAllDailyReports() {
        return openSessionReportDAO().getAllDailyReport();
    }


    public DailyReport getLastReport() {
        return openSessionReportDAO().getLastReport();
    }

    public void deleteAllReports() {
        openSessionReportDAO().deleteAllReports();
    }

    private DailyReportDao openSessionReportDAO() {
        return new DailyReportDao(sessionFactory.openSession());
    }
}
