import java.util.ArrayList;
import java.util.List;

public class QuizSection extends ArrayList<QuizElement> implements IQuizEntry {

    private List<QuizElement> quizElements = new ArrayList<>();
    private String name;

    public QuizSection(String name) {
        this.name = name;
    }

    public QuizSection(String name, List<QuizElement> elements) {
        this.name = name;
        quizElements = elements;
    }

    public QuizSection(String name, QuizElement... elements) {
        this.name = name;
        for (QuizElement element : elements) {
            quizElements.add(element);
        }
    }

    @Override
    public List<QuizElement> getAll() {
        return quizElements;
    }
}
