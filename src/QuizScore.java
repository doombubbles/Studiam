/**
 * QuizScore.java
 *
 * An object that stores a store after taking the quiz, also storing which terms were gotten wrong
 *
 * @version 09/18/18
 */

import java.util.ArrayList;
import java.util.List;

public class QuizScore {

    private int correct;
    private int total;
    private int time;
    private List<String> wrong;

    public QuizScore(int correct, int total, int time, List<String> wrong) {
        this.correct = correct;
        this.total = total;
        this.time = time;
        this.wrong = wrong;
    }

    public QuizScore(String dataString) {
        String[] splitStrings = dataString.split(",");
        correct = Integer.parseInt(splitStrings[0]);
        total = Integer.parseInt(splitStrings[1]);
        time = Integer.parseInt(splitStrings[2]);
        wrong = new ArrayList<>();
        for (int i = 3; i < splitStrings.length; i++) {
            wrong.add(splitStrings[i]);
        }
    }

    public int getCorrect() {
        return correct;
    }

    public int getTime() {
        return time;
    }

    public int getTotal() {
        return total;
    }

    public String toNiceString() {
        return correct + "/" + total + " - " +
                (Math.round(10000 * correct / total) / 100.0) + "%" + " in " + time + " seconds.";
    }

    @Override //method to convert this score into a string for saving
    public String toString() {
        String s = correct + "," + total + "," + time;
        for (String term : wrong) {
            s += "," + term;
        }
        return s;
    }
}
