package mz.periferals;


/**
 * This class is used for Observable list which is used for
 * generating the TableView content.
 * (I am using JSON-Simple maven dependency and the result of the
 * played game is saved in json file under /spaceGame/arch.json)
 * Getters and Setter should be present
 */

public class Archive {
    private String date, time, score;

    public Archive(String date, String score, String time) {
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
