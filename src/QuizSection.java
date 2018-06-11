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

    public String getName() {
        return name;
    }

    @Override
    public List<QuizElement> getAll() {
        return this;
    }

    @Override
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
