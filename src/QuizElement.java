import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizElement extends ArrayList<QuizTerm> implements IQuizEntry {

    public String dataString = "";

    public QuizElement(String dataString) {
        this.dataString = dataString;
    }

    @Override
    public String toString() {
        return dataString;
    }

    @Override
    public List<QuizElement> getAll() {
        return Arrays.asList(this);
    }
}
