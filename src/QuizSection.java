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

    @Override
    public List<QuizElement> getAll() {
        return this;
    }
}
