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

    @Override
    public String toString() {
        return dataString;
    }

    @Override
    public List<QuizElement> getAll() {
        return Arrays.asList(this);
    }
}
