package by.sanko.selectioncommittee.controller.filter;

import by.sanko.selectioncommittee.controller.MappingJSP;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.AuthenticationService;
import by.sanko.selectioncommittee.service.ServiceFactory;
import by.sanko.selectioncommittee.service.impl.AuthenticationServiceImpl;
import by.sanko.selectioncommittee.util.security.JWTUtil;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {
    private static final String REG_COOK_ATTRIBUTE = "registrationToken";
    private static final String AUTH_COOK_ATTRIBUTE = "authorizationToken";
    private static final String ID_PARAMETER = "userID";
    private static final String FACULTY_ID_PARAMETER = "facultyID";
    private static final String ROLE_PARAMETER = "userRole";
    private static final String NAME_PARAMETER = "userName";
    private static final String LASTNAME_PARAMETER = "userLastName";
    private static final String FATHERS_NAME_PARAMETER = "userFathersName";
    private static final String COMMAND = "command";
    private static final String LOGIN_COMMAND = "authorization";
    private static final String FORGOT_PASS_COMMAND = "CHANGINGPASSWORD";
    private static final String SET_NEW_PASS_COMMAND = "setNewPass";
    private static final String CHANGE_COMMAND = "changePass";
    private static final String SWITCH_LOCATION_COMMAND = "switchLocation";
    private static final String REGISTRAION_COMMAND = "registration";
    private static final String GET_ALL_FAC_COMMAND = "getFaculties";
    private static final String LOGIN_PARAMETER = "userLogin";
    private static final String ANSWER_PARAM = "answer";
    private static final String code = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(code);
        servletResponse.setCharacterEncoding(code);
        String path = ((HttpServletRequest) servletRequest).getRequestURI();
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (path.endsWith(MappingJSP.WELCOME_PAGE) || path.endsWith(MappingJSP.AUTHORIZATION) || path.endsWith(MappingJSP.SET_NEW_PASS) ||
                path.endsWith("/jsp/registration.jsp") || path.equals("/") || path.endsWith("/jsp/change_password.jsp")|| isCommandBad(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String redirectedPage = MappingJSP.WELCOME_PAGE;
            if(path.endsWith(MappingJSP.AUTO_LOGIN)){
                redirectedPage = MappingJSP.LOGIN_PAGE;
            }
            String regToken = JWTUtil.getInstance().getCook(servletRequest, REG_COOK_ATTRIBUTE);
            AuthenticationService authenticationService = ServiceFactory.getInstance().getAuthenticationService();
            String ipAddress = servletRequest.getRemoteAddr();
            if (!regToken.isEmpty()) {
                try {
                    if (authenticationService.verifyRegistrationToken(regToken, ipAddress)) {
                        String authToken = JWTUtil.getInstance().getCook(servletRequest, AUTH_COOK_ATTRIBUTE);
                        if (authToken.isEmpty() || !authenticationService.verifyAuthenticationToken(regToken, authToken, ipAddress)) {
                            authToken = authenticationService.createAuthenticationToken(regToken, ipAddress);
                            if(authToken.isEmpty()){
                                response.sendRedirect(redirectedPage);
                                return;
                            }
                            Cookie authorizationCook = new Cookie(AUTH_COOK_ATTRIBUTE, authToken);
                            response.addCookie(authorizationCook);
                        }
                        DecodedJWT authJWT = JWTUtil.getInstance().decodeJWT(authenticationService.createAuthenticationToken(authToken, ipAddress));
                        int id = authJWT.getClaim(authenticationService.getTokenParameter(ID_PARAMETER)).asInt();
                        String name = authJWT.getClaim(authenticationService.getTokenParameter(NAME_PARAMETER)).asString();
                        String role = authJWT.getClaim(authenticationService.getTokenParameter(ROLE_PARAMETER)).asString();
                        String lastName = authJWT.getClaim(authenticationService.getTokenParameter(LASTNAME_PARAMETER)).asString();
                        String login = authJWT.getClaim(authenticationService.getTokenParameter(LOGIN_PARAMETER)).asString();
                        String fatehrsName = authJWT.getClaim(authenticationService.getTokenParameter(FATHERS_NAME_PARAMETER)).asString();
                        request.setAttribute(ID_PARAMETER, id);
                        request.setAttribute(NAME_PARAMETER, name);
                        request.setAttribute(ROLE_PARAMETER, role);
                        request.setAttribute(LASTNAME_PARAMETER, lastName);
                        request.setAttribute(FATHERS_NAME_PARAMETER, fatehrsName);
                        request.setAttribute(LOGIN_PARAMETER, login);
                        String answer = request.getParameter(ANSWER_PARAM);
                        if(answer != null && !answer.isEmpty()){
                            request.setAttribute(ANSWER_PARAM,answer);
                        }
                        if(role.equals(UsersRole.ADMINISTRATOR.name())){
                            int facultyID = authJWT.getClaim(authenticationService.getTokenParameter(FACULTY_ID_PARAMETER)).asInt();
                            request.setAttribute(FACULTY_ID_PARAMETER, facultyID);
                        }
                    } else {
                        response.sendRedirect(redirectedPage);
                        return;
                    }
                } catch (ServiceException ignored) {
                    response.sendRedirect(redirectedPage);
                    return;
                }
            } else {
                response.sendRedirect(redirectedPage);
                return;
            }
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isCommandBad(HttpServletRequest request){
        boolean isCommandBad = false;
        if(request.getParameter(COMMAND) != null){
            String command = request.getParameter(COMMAND);
            if(command.equals(LOGIN_COMMAND) || command.equals(GET_ALL_FAC_COMMAND) || command.equals(FORGOT_PASS_COMMAND) || command.equals(SWITCH_LOCATION_COMMAND) ||
                    command.equals(REGISTRAION_COMMAND) || command.equals(SET_NEW_PASS_COMMAND) || command.equals(CHANGE_COMMAND)){
                isCommandBad = true;
            }
        }
        return isCommandBad;
    }
}
