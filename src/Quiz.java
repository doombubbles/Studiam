import java.util.ArrayList;
import java.util.List;

public class Quiz extends ArrayList<IQuizEntry> {

    public String name;
    public String description;
    public int percent;

    public List<QuizElement> getAllElements() {
        List<QuizElement> list = new ArrayList<>();
        for (IQuizEntry entry : this) {
            list.addAll(entry.getAll());
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Quiz) {
            Quiz otherQuiz = (Quiz) o;
            if (size() != otherQuiz.size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (!get(i).equals(otherQuiz.get(i))) {
                    return false;
                }
            }

            return name.equals(otherQuiz.name) && description.equals(otherQuiz.description) && percent == otherQuiz.percent;
        }
        return super.equals(o);
    }
}
