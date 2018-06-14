import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class QuizFile extends File {

    public Quiz quiz;

    public QuizFile(String pathname) {
        super(pathname);
    }

    public QuizFile(File file) {
        super(file.getPath() + (file.getPath().endsWith(".studiam") ? "" : ".studiam"));
    }

    public void saveScore(QuizScore score) {
        PrintStream output;
        try {
            output = new PrintStream(new FileOutputStream(this, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        output.println("quizScore = " + score.toString());
    }

    public Quiz createQuiz() {
        Scanner scanner;
        try {
            scanner = new Scanner(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        quiz = new Quiz();

        QuizSection section = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String type = line.substring(0, line.indexOf(" "));
            String data = line.substring(line.indexOf("=") + 2);
            switch (type) {
                case "quizName":
                    quiz.name = data;
                    break;
                case "quizDesc":
                    quiz.description = data;
                    break;
                case "quizPercent":
                    quiz.percent = Integer.parseInt(data);
                    break;
                case "quizMaxRemoved":
                    quiz.maxRemoved = Integer.parseInt(data);
                    break;
                case "quizSection":
                    if (section == null) {
                        section = new QuizSection(data);
                    } else {
                        quiz.add(section); //add the previous section
                        section = new QuizSection(data);
                    }
                    break;
                case "quizElement":
                    QuizElement element = new QuizElement(data);

                    if (section == null) {
                        quiz.add(element);
                    } else {
                        section.add(element);
                    }
                    break;
                case "quizScore":
                    QuizScore score = new QuizScore(data);
                    quiz.addScore(score);
                default:
                    break;
            }
        }

        //add the last section
        if (section != null) {
            quiz.add(section);
        }

        return quiz;
    }


}
