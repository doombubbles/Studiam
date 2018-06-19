/**
 * QuizElement.java
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
import java.util.Arrays;
import java.util.List;

public class QuizElement extends ArrayList<QuizTerm> implements IQuizEntry {

    public String dataString = "";

    public QuizElement(String dataString) {
        this.dataString = dataString;
        String[] splitData = dataString.split(", ");
        for (String s : splitData) {
            String[] splitAgainData = s.split(" \\| ");
            QuizTerm term = new QuizTerm(splitAgainData[0]);
            for (int i = 1; i < splitAgainData.length; i++) {
                term.addAlternate(splitAgainData[i]);
            }
            add(term);
        }
    }

    public QuizElement() {
    }

    //method to convert this QuizElement back into a dataString to be saved
    public String convertBack() {
        String string = "";
        for (QuizTerm quizTerm : this) {
            string = string + quizTerm;
            for (String s : quizTerm.getAlternates()) {
                string = string + (" | " + s);
            }
            string += ", ";
        }
        return string.substring(0, string.length() - 2);
    }

    @Override //method to get the dataString
    public String toString() {
        return dataString;
    }

    @Override //method to get all the quiz elements that this quiz element is
    public List<QuizElement> getAll() {
        return Arrays.asList(this);
    }

    @Override //method to see if this quiz element equals another object (quiz element)
    public boolean equals(Object o) {
        if (o instanceof QuizElement) {
            QuizElement quizElement = (QuizElement) o;

            return convertBack().equals(quizElement.convertBack());
        }
        return super.equals(o);
    }
}
