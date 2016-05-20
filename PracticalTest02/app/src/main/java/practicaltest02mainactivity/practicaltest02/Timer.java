package practicaltest02mainactivity.practicaltest02;

/**
 * Created by student on 5/20/16.
 */
public class Timer {

    public String hour;
    public String minute;

    public Timer(String hour, String minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }


}
