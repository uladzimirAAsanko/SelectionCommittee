package by.sanko.selectioncommittee.util.mail;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MessageCreator {
    private static final String FILE_PROPERTIES = "prop/mails_content";
    private static final String INTRODUCTION_PARAMETER = "all.intro";
    private static final String CHANGE_PASS_SUBJECT = "cp.subject";
    private static final String SUCCESS_ENROLLMENT_SUBJECT = "se.subject";
    private static final String UN_SUCCESS_ENROLLMENT_SUBJECT = "use.subject";
    private static final String DELETING_STATEMENT_SUBJECT = "ds.subject";
    private static final String DELETING_STATEMENT_TEXT = "ds.text";
    private static final String SUCCESS_ENROLLMENT_TEXT = "se.text";
    private static final String UN_SUCCESS_ENROLLMENT_TEXT = "use.text";
    private static final String CHANGE_PASS_TEXT = "cp.text";
    private static final String END_OF_MESSAGE = "all.end";
    private static final Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FILE_PROPERTIES);
        map.put(INTRODUCTION_PARAMETER,resourceBundle.getString(INTRODUCTION_PARAMETER));
        map.put(CHANGE_PASS_SUBJECT,resourceBundle.getString(CHANGE_PASS_SUBJECT));
        map.put(SUCCESS_ENROLLMENT_SUBJECT,resourceBundle.getString(SUCCESS_ENROLLMENT_SUBJECT));
        map.put(SUCCESS_ENROLLMENT_TEXT,resourceBundle.getString(SUCCESS_ENROLLMENT_TEXT));
        map.put(CHANGE_PASS_TEXT,resourceBundle.getString(CHANGE_PASS_TEXT));
        map.put(DELETING_STATEMENT_SUBJECT,resourceBundle.getString(DELETING_STATEMENT_SUBJECT));
        map.put(UN_SUCCESS_ENROLLMENT_SUBJECT,resourceBundle.getString(UN_SUCCESS_ENROLLMENT_SUBJECT));
        map.put(DELETING_STATEMENT_TEXT,resourceBundle.getString(DELETING_STATEMENT_TEXT));
        map.put(UN_SUCCESS_ENROLLMENT_TEXT,resourceBundle.getString(UN_SUCCESS_ENROLLMENT_TEXT));
        map.put(END_OF_MESSAGE,resourceBundle.getString(END_OF_MESSAGE));
    }
    private MessageCreator(){}

    public static boolean writeChangePasswordMessage(String recipientAddress, String firstName, String lastName, String fathersName, String generatedLink){
        StringBuilder text = new StringBuilder();
        text.append(map.get(INTRODUCTION_PARAMETER));
        text.append(lastName).append(" ").append(firstName).append(" ").append(fathersName).append('\n');
        text.append(map.get(CHANGE_PASS_TEXT)).append('\n');
        text.append(generatedLink).append('\n');
        text.append(map.get(END_OF_MESSAGE));
        return MailSender.sendMail(recipientAddress,text.toString(),map.get(CHANGE_PASS_SUBJECT));
    }

    public static boolean writeSuccessfullEnrollment(String recipientAddress, String firstName, String lastName, String fathersName, String facultyName){
        StringBuilder text = new StringBuilder();
        text.append(map.get(INTRODUCTION_PARAMETER));
        text.append(lastName).append(" ").append(firstName).append(" ").append(fathersName).append('\n');
        text.append(map.get(SUCCESS_ENROLLMENT_TEXT)).append('\n').append(facultyName).append('\n');
        text.append(map.get(END_OF_MESSAGE));
        return MailSender.sendMail(recipientAddress,text.toString(),map.get(SUCCESS_ENROLLMENT_SUBJECT));
    }

    public static boolean writeUnSuccessfullyEnrollment(String recipientAddress, String firstName, String lastName, String fathersName, String facultyName){
        StringBuilder text = new StringBuilder();
        text.append(map.get(INTRODUCTION_PARAMETER));
        text.append(lastName).append(" ").append(firstName).append(" ").append(fathersName).append('\n');
        text.append(map.get(UN_SUCCESS_ENROLLMENT_TEXT)).append('\n').append(facultyName).append('\n');
        text.append(map.get(END_OF_MESSAGE));
        return MailSender.sendMail(recipientAddress,text.toString(),map.get(UN_SUCCESS_ENROLLMENT_SUBJECT));
    }

    public static boolean writeMessageWithDeletingStatement(String recipientAddress, String firstName, String lastName, String fathersName, String facultyName){
        StringBuilder text = new StringBuilder();
        text.append(map.get(INTRODUCTION_PARAMETER));
        text.append(lastName).append(" ").append(firstName).append(" ").append(fathersName).append('\n');
        text.append(map.get(DELETING_STATEMENT_TEXT)).append('\n').append(facultyName).append('\n');
        text.append(map.get(END_OF_MESSAGE));
        return MailSender.sendMail(recipientAddress,text.toString(),map.get(DELETING_STATEMENT_SUBJECT));
    }
}
