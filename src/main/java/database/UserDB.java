package database;

import entities.User;

import java.util.*;

public class UserDB {
    /*
    This class represents a Simple standalone DB which can store/retrieve collections of UserObject
    Does not have any form of filtering or constraints based querying support.
     */

    private static final Map<Integer, User> DB = new HashMap<>();
    private static int userIndex = 1;

    public static Collection<User> saveUsers(Collection<User> users) {
        List<User> usersAdded = new ArrayList<>();
        for (User user : users) {
            if (user.getId() == -1) user.setId(userIndex++);
            usersAdded.add(DB.put(user.getId(), user));
        }
        return usersAdded;
    }

    public static Collection<User> getUsers() {
        return DB.values();
    }

    public static Collection<User> deleteUsers(Collection<User> users) {
        List<User> usersDeleted = new ArrayList<>();
        for (User user : users) {
            usersDeleted.add(DB.remove(user.getId()));
        }
        return usersDeleted;
    }

}
