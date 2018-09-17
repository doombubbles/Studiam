/**
 * QuizTerm.java
 *
 * One quizzed term/word, like canis
 * Part of a larger {@link QuizElement}
 *
 * @version 09/17/18
 */

import java.util.ArrayList;
import java.util.List;

public class QuizTerm {

    private String term;
    private List<String> alternates = new ArrayList<>();

    public QuizTerm(String term) {
        this.term = term;
    }

    public QuizTerm(String term, String... alternates) {
        this.term = term;
        for (String alternate : alternates) {
            this.alternates.add(alternate);
        }
    }

    //method to add a potential alternate to this term
    public void addAlternate(String string) {
        alternates.add(string);
    }

    //method to get the whole list of alternates for this term
    public List<String> getAlternates() {
        return alternates;
    }

    @Override //method to get the main term as a string
    public String toString() {
        return term;
    }

    //method to check if a string is correct for this term
    public boolean matches(String s) {
        return term.equalsIgnoreCase(s) || alternates.stream()
                .anyMatch(s1 -> s1.toLowerCase().equals(s.toLowerCase()));
    }

    @Override //method to see if this quiz term equals an object (quiz term)
    public boolean equals(Object obj) {
        if (obj instanceof QuizTerm) {
            QuizTerm quizTerm = (QuizTerm) obj;
            if (quizTerm.getAlternates().size() != alternates.size()) {
                return false;
            }
            for (int i = 0; i < alternates.size(); i++) {
                if (!alternates.get(i).equals(quizTerm.getAlternates().get(i))) {
                    return false;
                }
            }
            return quizTerm.toString().equals(term);
        } else return super.equals(obj);
    }
}
