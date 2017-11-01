import java.util.Date;

public class DayData {

    private Date date;
    private double open, high, low, close;

    public DayData(Date date1, double open1, double high1, double low1, double close1) {
        date = date1;
        open = open1;
        high = high1;
        low = low1;
        close = close1;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public boolean isYang() {
        if (close > open)
            return true;
        else
            return false;
    }
}
