/**
 * QuizSection.java
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

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class QuizSection extends ArrayList<QuizElement> implements IQuizEntry {

    private String name;

    public QuizSection(String name) {
        this.name = name;
    }

    public QuizSection(String name, List<QuizElement> elements) {
        this.name = name;
        for (QuizElement element : elements) {
            add(element);
        }
    }

    public QuizSection(String name, QuizElement... elements) {
        this.name = name;
        for (QuizElement element : elements) {
            add(element);
        }
    }

    //method to get the name of the quiz
    public String getName() {
        return name;
    }

    @Override //method to get all quiz elements of this quiz section
    public List<QuizElement> getAll() {
        return this;
    }

    @Override //method to see if this quiz sections equals another object (quiz section)
    public boolean equals(Object o) {
        if (o instanceof QuizSection) {
            QuizSection quizSection = (QuizSection) o;
            if (quizSection.size() != size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (!get(i).equals(quizSection.get(i))) {
                    return false;
                }
            }
            return name.equals(quizSection.getName());
        }
        return super.equals(o);
    }
}
