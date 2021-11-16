package mz.periferals;

public class Archive {
    private String date, time, score;

    public Archive(String date, String time, String score) {
        this.date = date;
        this.time = time;
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
