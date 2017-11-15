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

    public boolean isHammer() {
        if (close > open) {       //if this day rises
            if (open - low >= 2 * (close - open) && high - close <= 0.2 * (high - low))  // The length of the lower wick is at least 2 times the height of the body && The length of the upper wick is less than 20% of total length
                return true;
        } else if (close < open) {    //if this day falls
            if (close - open >= 2 * (open - close) && high - open <= 0.2 * (high - low))
                return true;
        } else if (close == open) {
            if (high - open <= 0.2 * (high - low))
                return true;
        }
        return false;
    }
}
