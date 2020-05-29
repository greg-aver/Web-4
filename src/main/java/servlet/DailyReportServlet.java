package servlet;

import com.google.gson.Gson;
import service.DailyReportService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            DailyReportService dailyReportService = DailyReportService.getInstance();
            Gson gson = new Gson();
            String json;
            if (req.getPathInfo().contains("all")) {
                json = gson.toJson(dailyReportService.getAllDailyReports());
            } else if (req.getPathInfo().contains("last")) {
                json = gson.toJson(dailyReportService.getLastReport());
            } else {
                json = "You entered the wrong request";
            }
            resp.getWriter().write(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.getStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            DailyReportService dailyReportService = DailyReportService.getInstance();
            dailyReportService.deleteAllReports();
            resp.getWriter().println("All reports and cars was DELETE");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.getStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
