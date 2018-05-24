import java.util.HashSet;
import java.util.Set;

public class QuizTerm {

    private String term;
    private Set<String> alternates = new HashSet<>();

    public QuizTerm(String term) {
        this.term = term;
    }

    public QuizTerm(String term, String... alternates) {
        this.term = term;
        for (String alternate : alternates) {
            this.alternates.add(alternate);
        }
    }

    public void addAlternate(String string) {
        alternates.add(string);
    }

    @Override
    public String toString() {
        return term;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            String string = (String) obj;
            return term.equals(string) || alternates.contains(string);
        } else return super.equals(obj);
    }
}
