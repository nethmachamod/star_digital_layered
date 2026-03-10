package lk.ijse.star_digitalBook.dto;

public class DailyChartDTO {
    private String date;
    private int value;

    public DailyChartDTO(String date, int value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DailyChartDTO{" + "date=" + date + ", value=" + value + '}';
    }
    
    
    
}
