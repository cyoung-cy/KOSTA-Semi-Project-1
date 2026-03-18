package dto;

public class WeeklyStat {
    private int dayOfWeek;    // DAYOFWEEK (1=일 ~ 7=토)
    private int count;        // 예매 수
    private int dailySales;   // 일일 매출

    public WeeklyStat() {}

    public WeeklyStat(int dayOfWeek, int count, int dailySales) {
        this.dayOfWeek = dayOfWeek;
        this.count = count;
        this.dailySales = dailySales;
    }

    public int getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    public int getDailySales() { return dailySales; }
    public void setDailySales(int dailySales) { this.dailySales = dailySales; }
}