package model;

import org.hibernate.Session;

import javax.persistence.*;

@Entity(name = "DailyReport")
@Table(name = "daily_reports")
public class DailyReport {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "earnings")
    private long earnings;

    @Column(name = "soldCars")
    private long soldCars;

    public DailyReport() {

    }

    public DailyReport(long earnings, long soldCars) {
        this.earnings = earnings;
        this.soldCars = soldCars;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEarnings() {
        return earnings;
    }

    public void setEarnings(long earnings) {
        this.earnings = earnings;
    }

    public long getSoldCars() {
        return soldCars;
    }

    public void setSoldCars(long soldCars) {
        this.soldCars = soldCars;
    }
}
