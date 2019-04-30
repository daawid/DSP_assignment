package com.models;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author dawid
 */
/**
 * Uses 'User' POJO, Users/Members are related, members have separate DAO
 */
public class UserDAO implements GenericDAO<User, String> {

    private Database db;

    public UserDAO() {
        db = new Database();
    }

    //Add user record to the database
    @Override
    public boolean create(User newUser) {
        boolean inserted = db.insertData(Database.TBL_USERS, newUser);

        return inserted;
    }

    //Generate a list of all users
    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_USERS)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }

    //Get users by status
    public List<User> getAllByStatus(String status) {
        ArrayList<User> users = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_USERS, Database.TBL_USERS_STATUS, status)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }

    //Get user by ID
    @Override
    public User getByID(String id) {

        User user = (User) db.searchData(Database.TBL_USERS, Database.TBL_USERS_ID, id);
        if (user == null) {
            System.out.println("UserDAO error, not found");
        }
        return user;
    }

    //Update user password
    public boolean updatePassword(User updatedUser) {

        return db.updateData(Database.TBL_USERS, updatedUser.getUsername(), Database.TBL_USERS_PASSWORD, updatedUser.getPassword());

    }

    //Update user status
    public boolean updateStatus(User updatedUser) {
        return db.updateData(Database.TBL_USERS, updatedUser.getUsername(), Database.TBL_USERS_STATUS, updatedUser.getStatus());

    }

    //Update user reset status
    public boolean updateReset(User updatedUser) {
        return db.updateData(Database.TBL_USERS, updatedUser.getUsername(), Database.TBL_USERS_RESET, updatedUser.getReset());
    }

}
