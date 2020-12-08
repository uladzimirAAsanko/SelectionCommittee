package by.sanko.selectioncommittee.service;

import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.ServiceException;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface AuthenticationService {

    String createRegistrationToken(int userID, String userIP) throws ServiceException;

    boolean verifyRegistrationToken(String token, String userIP) throws ServiceException;

    String createAuthenticationToken(String registrationToken,  String userIP) throws ServiceException;

    boolean verifyAuthenticationToken(String registrationToken, String token, String userIP) throws ServiceException;

    String getTokenParameter(String param);

    String createChangingPassToken(User user) throws ServiceException;

    int takeUserIDFromToken(String token) throws ServiceException;
}
