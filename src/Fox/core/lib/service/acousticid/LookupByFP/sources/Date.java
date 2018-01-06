package Fox.core.lib.service.acousticid.LookupByFP.sources;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date() {
    }

    public Date(int day,
                int month,
                int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean hasDay() {
        return day != 0;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public boolean hasMonth() {
        return month != 0;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean hasYear() {
        return year != 0;
    }
}
