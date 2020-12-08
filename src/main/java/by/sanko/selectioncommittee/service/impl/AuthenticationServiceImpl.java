package by.sanko.selectioncommittee.service.impl;

import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UserStatus;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.DaoException;
import by.sanko.selectioncommittee.exception.ServiceException;
import by.sanko.selectioncommittee.service.AuthenticationService;
import by.sanko.selectioncommittee.util.security.JWTUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.util.*;

public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String AUTHOR_PARAMETER = "author";
    private static final String REGISTRATION_TOKEN_TIME_PARAMETER = "registrationExpHours";
    private static final String AUTH_TOKEN_TIME_PARAMETER = "authExpMin";
    private static final String CHANGE_PASS_TOKEN_TIME_PARAMETER = "changePassExpMin";
    private static final String FILE_JWT_CONFIG = "config/token";
    private static final String SECRET_PARAMETER = "secretWord";
    private static final String IP_PARAMETER = "userIP";
    private static final String ID_PARAMETER = "userID";
    private static final String ROLE_PARAMETER = "userRole";
    private static final String NAME_PARAMETER = "userName";
    private static final String LASTNAME_PARAMETER = "userLastName";
    private static final String FATHERS_NAME_PARAMETER = "userFathersName";
    private static final String LOGIN_PARAMETER = "userLogin";
    private static final String FACULTY_ID_PARAMETER = "facultyID";
    private static final Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(FILE_JWT_CONFIG);
        map.put(SECRET_PARAMETER,resourceBundle.getString(SECRET_PARAMETER));
        map.put(AUTHOR_PARAMETER,resourceBundle.getString(AUTHOR_PARAMETER));
        map.put(REGISTRATION_TOKEN_TIME_PARAMETER,resourceBundle.getString(REGISTRATION_TOKEN_TIME_PARAMETER));
        map.put(AUTH_TOKEN_TIME_PARAMETER,resourceBundle.getString(AUTH_TOKEN_TIME_PARAMETER));
        map.put(IP_PARAMETER,resourceBundle.getString(IP_PARAMETER));
        map.put(ID_PARAMETER,resourceBundle.getString(ID_PARAMETER));
        map.put(ROLE_PARAMETER,resourceBundle.getString(ROLE_PARAMETER));
        map.put(NAME_PARAMETER,resourceBundle.getString(NAME_PARAMETER));
        map.put(LASTNAME_PARAMETER,resourceBundle.getString(LASTNAME_PARAMETER));
        map.put(FATHERS_NAME_PARAMETER,resourceBundle.getString(FATHERS_NAME_PARAMETER));
        map.put(LOGIN_PARAMETER,resourceBundle.getString(LOGIN_PARAMETER));
        map.put(CHANGE_PASS_TOKEN_TIME_PARAMETER,resourceBundle.getString(CHANGE_PASS_TOKEN_TIME_PARAMETER));
        map.put(FACULTY_ID_PARAMETER,resourceBundle.getString(FACULTY_ID_PARAMETER));
    }

    @Override
    public String createRegistrationToken(int userID, String userIP) throws ServiceException {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(map.get(SECRET_PARAMETER));
            LocalDateTime date = LocalDateTime.now();
            date = date.plusHours( Integer.parseInt(map.get(REGISTRATION_TOKEN_TIME_PARAMETER)));
            token = JWT.create()
                    .withIssuer(map.get(AUTHOR_PARAMETER))
                    .withExpiresAt(java.sql.Timestamp.valueOf(date))
                    .withClaim(map.get(IP_PARAMETER),userIP)
                    .withClaim(map.get(ID_PARAMETER),userID)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new ServiceException("Invalid Signing configuration / Couldn't convert Claims",exception);
        }
        return token;
    }

    @Override
    public boolean verifyRegistrationToken(String token, String userIP) throws ServiceException {
        boolean isVerified = true;
        try {
            Algorithm algorithm = Algorithm.HMAC256(map.get(SECRET_PARAMETER));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(map.get(AUTHOR_PARAMETER))
                    .withClaim(map.get(IP_PARAMETER),userIP)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Date currDate = new Date(System.currentTimeMillis());
            isVerified = jwt.getExpiresAt().after(currDate);
        } catch (JWTVerificationException exception){
            isVerified = false;
        }
        return isVerified;
    }

    @Override
    public String createAuthenticationToken(String registrationToken, String userIP) throws ServiceException {
        String token = "";
        DecodedJWT jwt = JWTUtil.getInstance().decodeJWT(registrationToken);
        String ip = jwt.getClaim(map.get(IP_PARAMETER)).asString();
        Integer id = jwt.getClaim(map.get(ID_PARAMETER)).asInt();
        if(ip == null || !ip.equals(userIP) || id == null){
            return token;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(map.get(SECRET_PARAMETER));
            LocalDateTime date = LocalDateTime.now();
            date = date.plusMinutes(Integer.parseInt(map.get(AUTH_TOKEN_TIME_PARAMETER)));

            User user = DaoFactory.getInstance().getUserDao().getUserByID(id);
            if(user.getStatus() == UserStatus.ACTIVE) {
                if(user.getRole() == UsersRole.ADMINISTRATOR){
                    int facultyID = DaoFactory.getInstance().getFacultyDao().getFacultyIdByUserID(user.getUserID());
                    token = JWT.create()
                            .withIssuer(map.get(AUTHOR_PARAMETER))
                            .withExpiresAt(java.sql.Timestamp.valueOf(date))
                            .withClaim(map.get(IP_PARAMETER), userIP)
                            .withClaim(map.get(ID_PARAMETER), id)
                            .withClaim(map.get(ROLE_PARAMETER), user.getRole().name())
                            .withClaim(map.get(NAME_PARAMETER), user.getFirstName())
                            .withClaim(map.get(LASTNAME_PARAMETER), user.getLastName())
                            .withClaim(map.get(FATHERS_NAME_PARAMETER), user.getFathersName())
                            .withClaim(map.get(LOGIN_PARAMETER), user.getLogin())
                            .withClaim(map.get(FACULTY_ID_PARAMETER),facultyID)
                            .sign(algorithm);
                }else {
                    token = JWT.create()
                            .withIssuer(map.get(AUTHOR_PARAMETER))
                            .withExpiresAt(java.sql.Timestamp.valueOf(date))
                            .withClaim(map.get(IP_PARAMETER), userIP)
                            .withClaim(map.get(ID_PARAMETER), id)
                            .withClaim(map.get(ROLE_PARAMETER), user.getRole().name())
                            .withClaim(map.get(NAME_PARAMETER), user.getFirstName())
                            .withClaim(map.get(LASTNAME_PARAMETER), user.getLastName())
                            .withClaim(map.get(FATHERS_NAME_PARAMETER), user.getFathersName())
                            .withClaim(map.get(LOGIN_PARAMETER), user.getLogin())
                            .sign(algorithm);
                }
            }
        } catch (JWTCreationException exception){
            throw new ServiceException("Invalid Signing configuration / Couldn't convert Claims",exception);
        } catch (DaoException e) {
            throw new ServiceException("Exception while getting role from user by id",e);
        }
        return token;
    }

    @Override
    public boolean verifyAuthenticationToken(String registrationToken, String token, String userIP) throws ServiceException {
        boolean isVerified = false;
        try {
            Algorithm algorithm = Algorithm.HMAC256(map.get(SECRET_PARAMETER));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(map.get(AUTHOR_PARAMETER))
                    .withClaim(map.get(IP_PARAMETER),userIP)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Date currDate = new Date(System.currentTimeMillis());
            String tokenIP = jwt.getClaim(map.get(IP_PARAMETER)).asString();
            DecodedJWT regToken = JWTUtil.getInstance().decodeJWT(registrationToken);
            int id = jwt.getClaim(map.get(ID_PARAMETER)).asInt();
            if(tokenIP !=null && !tokenIP.isEmpty() &&
                    tokenIP.equals(userIP) && regToken.getClaim(map.get(IP_PARAMETER)).asString().equals(userIP)){
                isVerified = true;
            }
            if(isVerified){
                isVerified =  jwt.getExpiresAt().after(currDate);
            }
            if(isVerified) {
                User user = DaoFactory.getInstance().getUserDao().getUserByID(id);
                isVerified = user.getStatus() == UserStatus.ACTIVE;
            }
        } catch (JWTVerificationException | DaoException exception){
            isVerified = false;
        }
        return isVerified;
    }

    @Override
    public String getTokenParameter(String param) {
        return map.get(param);
    }

    @Override
    public String createChangingPassToken(User user) throws ServiceException {
        String token = "";
        try {
            Algorithm algorithm = Algorithm.HMAC256(map.get(SECRET_PARAMETER));
                LocalDateTime date = LocalDateTime.now();
            date = date.plusMinutes( Integer.parseInt(map.get(CHANGE_PASS_TOKEN_TIME_PARAMETER)));
            token = JWT.create()
                    .withIssuer(map.get(AUTHOR_PARAMETER))
                    .withExpiresAt(java.sql.Timestamp.valueOf(date))
                    .withClaim(map.get(ID_PARAMETER),user.getUserID())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new ServiceException("Invalid Signing configuration / Couldn't convert Claims",exception);
        }
        return token;
    }

    @Override
    public int takeUserIDFromToken(String token) throws ServiceException {
        int userID = -1;
        if(token == null || token.isEmpty()){
            return userID;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(map.get(SECRET_PARAMETER));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(map.get(AUTHOR_PARAMETER))
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Date currDate = new Date(System.currentTimeMillis());
            if(jwt.getExpiresAt().after(currDate)){
                DecodedJWT changeToken = JWTUtil.getInstance().decodeJWT(token);
                userID = changeToken.getClaim(map.get(ID_PARAMETER)).asInt();
            }
        }catch (JWTVerificationException e){
            userID = -1;
        }
        return userID;
    }


}
