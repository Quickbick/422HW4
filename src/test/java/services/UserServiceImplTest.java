package services;

import Validator.UserValidator;
import dao.UserDao;
import dao.UserDaoImpl;
import database.UserDB;
import entities.Role;
import entities.User;
import exceptions.InvalidUserInfoException;
import exceptions.UserDBInconsistentException;
import exceptions.UserNotSavedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    //pairwise integration test for UserServiceImpl.createUser and the User Class/Constructor
    //Driver functions as UserService.createUser
    //Stubs of UserValidator and UserDao.saveUser
    @Test
    void createUser_user() throws InvalidUserInfoException, UserNotSavedException, UserDBInconsistentException {
        String inputFirst = "John";
        String inputLast = "Test";
        String inputRole = "ADMIN";
        User expectedUser = new User(inputFirst, inputLast, Role.ADMIN);
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserValidator mockValidator = Mockito.mock(UserValidator.class);
        when(mockValidator.validateUser(any(User.class))).thenReturn(true);
        when(mockDao.saveUser(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        UserServiceImpl service = new UserServiceImpl(mockDao, mockValidator);
        assertEquals(expectedUser, service.createUser(inputFirst, inputLast, inputRole));
    }

    //pairwise integration test for UserServiceImpl.createUser and UserDao.saveUser
    //Driver functions as UserService.createUser
    //Stubs of UserValidator, User constructor, and UserDB
    @Test
    void createUser_save() throws InvalidUserInfoException, UserNotSavedException, UserDBInconsistentException {
        String inputFirst = "John";
        String inputLast = "Test";
        String inputRole = "ADMIN";
        User expectedUser = mock(User.class);
        List<User> userList = new ArrayList<>();
        userList.add(expectedUser);
        UserDB mockDB = mock(UserDB.class);
        when(mockDB.saveUsers(any())).thenReturn(userList);
        UserDao userDao = new UserDaoImpl(mockDB);
        UserDao spyDao = spy(userDao);
        UserValidator mockValidator = mock(UserValidator.class);
        when(mockValidator.validateUser(any(User.class))).thenReturn(true);
        UserServiceImpl service = new UserServiceImpl(spyDao, mockValidator);
        assertEquals(expectedUser, service.createUser(inputFirst, inputLast, inputRole));
    }
}