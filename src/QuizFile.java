import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class QuizFile extends File {

    private String quizName;

    public QuizFile(String pathname) {
        super(pathname);
        initialize();
    }

    public QuizFile(File file) {
        super(file.getPath());
        initialize();
    }

    public String getQuizName() {
        return quizName;
    }

    public QuizFile initialize() {
        Scanner scanner;
        try {
            scanner = new Scanner(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String type = line.substring(0, line.indexOf(" "));
            String data = line.substring(line.indexOf("=") + 2);
            switch (type) {
                case "quizName":
                    quizName = data;
                    break;
                default:
                    break;
            }
        }


        return this;
    }


}
