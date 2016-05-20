package practicaltest02mainactivity.practicaltest02;

/**
 * Created by student on 5/20/16.
 */
public class Timer {

    public String ip;
    public String hour;
    public String minute;
    public String status;

    public Timer(String ip, String hour, String minute, String status) {
        this.ip = ip;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
