package com.models;

import java.sql.Date;


/**
 *
 * @author dawid
 */
public class RegistrationModel {

    String username, password;
    String firstName;
    String lastName;
    Date dob;
    String address;
    String email;

    public RegistrationModel(String firstName, String lastName, Date dob, String address, String email, String hashed_password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.username = email; // Email is used as the username
        this.password = hashed_password; // Hashed password is passed from the registration servlet which receives it as plain text
        this.email = email;
    }

    public User createAccount() {        
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setUsername(this.email);
        user.setPassword(this.password);
        user.setAddress(this.address);
        user.setDob(this.dob);
        user.setDor(new Date(System.currentTimeMillis())); // Date of registration
        user.setStatus(User.APPROVED); // Users are approved until admin decides otherwise
        user.setEmail(this.email);
        user.setReset(false);

        Database db = new Database();

        // Username as ID for both
        db.insertIntoMembers(this.username, this.firstName, this.lastName, this.address, this.email, this.dob, user.getDor(), user.getStatus(), user.getBalance());
        db.insertIntoUsers(this.username, this.password, user.getStatus(), false);
        return user;
    }

}
