package by.sanko.selectioncommittee.service.impl;


import by.sanko.selectioncommittee.dao.DaoFactory;
import by.sanko.selectioncommittee.dao.UserDao;
import by.sanko.selectioncommittee.dao.impl.UserDaoImpl;
import by.sanko.selectioncommittee.entity.RegistrationData;
import by.sanko.selectioncommittee.entity.User;
import by.sanko.selectioncommittee.entity.UserStatus;
import by.sanko.selectioncommittee.entity.UsersRole;
import by.sanko.selectioncommittee.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Rule;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockObjectFactory;
import org.testng.IObjectFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*",
        "com.sun.org.apache.xalan.*", "javax.management.*"})
@PrepareForTest({UserDaoImpl.class})
public class UserServiceImplTest {
    UserDaoImpl userDao;
    @ObjectFactory
    public IObjectFactory setObjectFactory() {
        return new PowerMockObjectFactory();
    }

    @BeforeMethod
    public void setUp() {
        PowerMockito.mockStatic(UserDaoImpl.class);
        userDao = Mockito.mock(UserDaoImpl.class);
        Mockito.when(UserDaoImpl.getInstance()).thenReturn(userDao);
    }



    @Test
    public void registerUserTest(){
        try {
            Mockito.when(userDao.isUserRegistered(Mockito.any())).thenReturn(false);
            Mockito.when(userDao.isEmailRegistered(Mockito.any())).thenReturn(false);
            Mockito.when(userDao.registration(Mockito.any())).thenReturn(true);
            UserServiceImpl userService = new UserServiceImpl();
            RegistrationData registrationData = new RegistrationData("Name","Lastname","fathersName","login","password","emal",1);
            boolean actual = userService.registerUser(registrationData);
            assertTrue(actual);
        }catch (Exception | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void addPhotoToUserTest(){
        try {
            Mockito.when(userDao.addPhotoToUser(1," DSA")).thenReturn(true);
            UserServiceImpl userService = new UserServiceImpl();
            boolean chek = userService.addPhotoToUser(1," DSA");
            assertTrue(chek);
        }catch (Exception | ServiceException e){
            fail(e.getMessage());
        }
    }



}
