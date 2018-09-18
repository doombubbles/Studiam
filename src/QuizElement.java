/**
 * QuizElement.java
 *
 * One element of a {@link Quiz} that is a list of {@link QuizTerm}s
 * ex. canis, canis, m, dog
 * (4 quizterms making up 1 quizelement)
 *
 * @version 09/18/18
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
