package by.sanko.selectioncommittee.util.parser;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParseDate {
    private static final String FORMAT_PATTERN ="yyyy-MM-dd";
    private ParseDate(){}

    public static Date parse(String data){
        Date sqlDate = null;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN);
        try {
            java.util.Date langDate = format.parse(data);
            sqlDate = new java.sql.Date(langDate.getTime());
        } catch (ParseException e) {
            return null;
        }
        return sqlDate;
    }
}
