package by.sanko.selectioncommittee.util.validator;

import by.sanko.selectioncommittee.util.parser.ParseDate;

import java.time.LocalDate;
import java.util.Date;

public class StatementValidator {
    private StatementValidator(){}

    public static boolean validateStatement(String expiredAt, int numberOfMaxStudents){
        if(expiredAt == null || expiredAt.isEmpty() || numberOfMaxStudents < 1){
            return false;
        }
        Date date = ParseDate.parse(expiredAt);
        if(date == null || date.before(java.sql.Date.valueOf(LocalDate.now()))){
            return false;
        }
        return true;
    }
}
