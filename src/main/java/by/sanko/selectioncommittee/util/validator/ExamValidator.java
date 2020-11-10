package by.sanko.selectioncommittee.util.validator;

public class ExamValidator {
    private static final ExamValidator instance = new ExamValidator();

    private ExamValidator() {

    }

    public static ExamValidator getInstance() {
        return instance;
    }

    public boolean validateExam(String exam){
        try {
            if (Integer.parseInt(exam) > 100)
                return false;
        }catch (NumberFormatException exception){
            return false;
        }
        return true;
    }
}
