import java.util.ArrayList;
import java.util.List;

public class QuizElement extends ArrayList<QuizTerm> {



    public QuizElement(QuizTerm... quizTerms) {
        for (QuizTerm quizTerm : quizTerms) {
            this.add(quizTerm);
        }
    }

    public QuizElement(List<QuizTerm> quizTermList) {
        this.addAll(quizTermList);
    }
}
