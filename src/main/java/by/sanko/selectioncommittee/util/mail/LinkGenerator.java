package by.sanko.selectioncommittee.util.mail;


public class LinkGenerator {
    private static final String SITE_ADDRESS = "http://localhost:8080/controller";
    private static final String COMMAND_PARAM = "?command=";
    private static final String CHANGING_PASSWORD_COMMAND = "CHANGINGPASSWORD";
    private static final String TOKEN_PARAM = "&token=";
    private LinkGenerator(){}

    public static String generateChangingPasswordLink(String token){
        StringBuffer buffer = new StringBuffer();

        buffer.append(SITE_ADDRESS).append(COMMAND_PARAM).append(CHANGING_PASSWORD_COMMAND).append(TOKEN_PARAM).append(token);
        return buffer.toString();
    }
}
