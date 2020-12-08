package by.sanko.selectioncommittee.util.security;

import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.AuthenticationService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class JWTUtil {
    private static final String REG_COOK_ATTRIBUTE = "registrationToken";
    private static final String AUTH_COOK_ATTRIBUTE = "authorizationToken";

    private static final JWTUtil instance = new JWTUtil();
    private JWTUtil(){}

    public static JWTUtil getInstance() {
        return instance;
    }

    public DecodedJWT decodeJWT(String token){
        DecodedJWT jwt = null;
        jwt =  com.auth0.jwt.JWT.decode(token);
        return jwt;
    }

    public void writeCook(HttpServletRequest req, HttpServletResponse resp, User user) throws ServiceException {
        AuthenticationService authenticationService = ServiceFactory.getInstance().getAuthenticationService();
        String ipAddress = "";
        if (req != null) {
            ipAddress = req.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || "".equals(ipAddress)) {
                ipAddress = req.getRemoteAddr();
            }
        }
        //String ipAddress = req.getRemoteAddr();
        String jwtREG = authenticationService.createRegistrationToken(user.getUserID(), ipAddress);
        String jwtAUTH = authenticationService.createAuthenticationToken(jwtREG, ipAddress);
        Cookie registrationCook = new Cookie(REG_COOK_ATTRIBUTE,jwtREG);
        Cookie authorizationCook = new Cookie(AUTH_COOK_ATTRIBUTE,jwtAUTH);
        resp.addCookie(registrationCook);
        resp.addCookie(authorizationCook);
    }

    public void deleteCook(HttpServletRequest req, HttpServletResponse resp){
        Cookie regToken = getCook(req, REG_COOK_ATTRIBUTE);
        Cookie authToken = getCook(req, REG_COOK_ATTRIBUTE);
        if(regToken != null) {
            regToken.setMaxAge(0);
            resp.addCookie(regToken);
        }
        if(authToken != null) {
            authToken.setMaxAge(0);
            resp.addCookie(authToken);
        }

    }

    public String getCook(ServletRequest servletRequest, String cookName){
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        Cookie[] cookies = request.getCookies();
        String answer = "";
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if (cook.getName().equals(cookName)) {
                    answer = cook.getValue();
                }
            }
        }
        return answer;
    }

    public Cookie getCook(HttpServletRequest servletRequest, String cookName){
        Cookie[] cookies = servletRequest.getCookies();
        Cookie answer = null;
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if (cook.getName().equals(cookName)) {
                    answer = cook;
                }
            }
        }
        return answer;
    }


}
