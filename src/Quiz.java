/**
 * Quiz.java
 *
 * An actual Quiz object, containing all the different {@link IQuizEntry} objects as well as
 * information about the Quiz like its name ({@link Quiz#name}), description ({@link Quiz#description}),
 * what percent of entries should be removed for taking the quiz ({@link Quiz#percent}),
 * and the maximum number of entries to remove ({@link Quiz#maxRemoved})
 *
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
