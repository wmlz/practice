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
        if (close > open) {       //如果这天是阳线
            if (open - low >= 2 * (close - open) && high - close <= 0.2 * (high - low))  // 下影线的长度至少达到实体高度的2倍 && 上影线小于整体长度的20%
                return true;
        } else if (close < open) {    //如果这天是阴线
            if (close - open >= 2 * (open - close) && high - open <= 0.2 * (high - low))
                return true;
        } else if (close == open) {
            if (high - open <= 0.2 * (high - low))
                return true;
        }
        return false;
    }
}
