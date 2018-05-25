import java.util.ArrayList;
import java.util.List;

public class Quiz extends ArrayList<IQuizEntry> {

    public String name;
    public String description;

    public Quiz() {
    }

    public Quiz(List<QuizElement> elements) {
        addAll(elements);
    }


    public List<QuizElement> getAllElements() {
        List<QuizElement> list = new ArrayList<>();
        for (IQuizEntry entry : this) {
            list.addAll(entry.getAll());
        }
        return list;
    }
}
