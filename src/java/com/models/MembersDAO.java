package com.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dawid
 */
/**
 * Uses 'User' POJO, Users/Members are related, users have separate DAO
 */
public class MembersDAO implements GenericDAO<User, String> {

    private Database db;

    public MembersDAO() {
        db = new Database();
    }

    ////Create user entry in db
    @Override
    public boolean create(User newMember) {
        boolean inserted = db.insertData(Database.TBL_MEMBERS, newMember);
        return inserted;
    }

    //Generate a list of all members
    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_MEMBERS)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }

    //Get members by status
    public List<User> getAllByStatus(String status) {
        ArrayList<User> users = new ArrayList<>();

        for (Object object : db.getAllData(Database.TBL_MEMBERS, Database.TBL_MEMBERS_STATUS, status)) {
            User user = (User) object;
            users.add(user);
        }

        return users;
    }

    //Return members with a balance larger than zero
    public List<User> listBalances() {
        ArrayList<User> users = new ArrayList<>();
        for (Object object : db.getAllData(Database.TBL_MEMBERS)) {
            User user = (User) object;
            if (user.getBalance() > 0) {
                users.add(user);
            }
        }
        return users;
    }

    //Get member by ID
    @Override
    public User getByID(String id) {
        User user = (User) db.searchData(Database.TBL_MEMBERS, Database.TBL_MEMBERS_ID, id);
        if (user == null) {
            System.out.println("MemberDAO error, not found");
        }
        //other classes need to careful to see if this is null or not.
        return user;
    }

    //Update user status
    public boolean updateStatus(User updatedUser) {
        return db.updateData(Database.TBL_MEMBERS, updatedUser.getUsername(), Database.TBL_MEMBERS_STATUS, updatedUser.getStatus());
    }

    //Update member balance
    public boolean updateBalance(User updatedUser) {
        return db.updateData(Database.TBL_MEMBERS, updatedUser.getUsername(), Database.TBL_MEMBERS_BALANCE, updatedUser.getBalance());
    }

}
