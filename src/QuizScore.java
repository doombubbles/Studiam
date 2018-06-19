/**
 * QuizScore.java
 * Assignment: Final Project - Studiam
 * Purpose:Studiam would be a tool used to help memorize specific vocabulary for classes and
 * practice for tests, particularly for learning complex languages like Latin. Users would
 * be able to type up or import vocabulary lists for classes into the software and practice
 * with the material. The software would randomly generate mock-up practice tests with all
 * of the content, optionally timed, and we give immediate feedback. It would also have the
 * tools to make the test as “smart” as possible, i.e. accepting multiple potential answers,
 * common spelling mistakes, punctuation and capitalization accepted. The focus would also
 * be on user-friendliness, and much of the development would be focused on making an intuitive,
 * aesthetically pleasing user interface that could be piloted well even without much experience.
 *
 * @version 06/19/18
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
