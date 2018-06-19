/**
 * Quiz.java
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

public class Quiz extends ArrayList<IQuizEntry> {

    public String name;
    public String description;
    public int percent;
    public int maxRemoved;
    private List<QuizScore> scores;

    public Quiz() {
        scores = new ArrayList<>();
    }

    public Quiz(String name) {
        this.name = name;
        scores = new ArrayList<>();
    }

    public Quiz(String name, String description) {
        this.name = name;
        this.description = description;
        scores = new ArrayList<>();
    }

    //method to get the scores of this quiz
    public List<QuizScore> getScores() {
        return scores;
    }

    //method to add a past score to this quiz
    public void addScore(QuizScore score) {
        scores.add(score);
    }

    //method to get ALL the elements of this quiz
    public List<QuizElement> getAllElements() {
        List<QuizElement> list = new ArrayList<>();
        for (IQuizEntry entry : this) {
            list.addAll(entry.getAll());
        }
        return list;
    }

    @Override //method to see if this quiz equals another object (quiz)
    public boolean equals(Object o) {
        if (o instanceof Quiz) {
            Quiz otherQuiz = (Quiz) o;
            if (size() != otherQuiz.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (!get(i).equals(otherQuiz.get(i))) {
                    return false;
                }
            }

            return name.equals(otherQuiz.name)
                    && description.equals(otherQuiz.description)
                    && percent == otherQuiz.percent;
        }
        return super.equals(o);
    }
}
