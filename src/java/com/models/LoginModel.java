package com.models;


/**
 *
 * @author dawid
 */

public class LoginModel {

    private User user;
    private String hashed_password;
    Password pw = new Password();

    public LoginModel(String username, String password) {
        Database db = new Database();
        //if (pw.checkPassword(password, user.getPassword()) == true) {
        user = (User) db.searchData(Database.TBL_USERS, Database.TBL_USERS_ID, username);

        //}
    }

    //Validates user based on username and password
    public boolean validateUser(String username, String password) {
        if (user != null) {
            if (pw.checkPassword(password, user.getPassword())) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }


    public String getUsername() {
        return user.getUsername();
    }

    public String getPassword() {
        return user.getPassword();
    }
    
    public boolean getReset() {
        return user.getReset();
    }

    public String getStatus() {
        return user.getStatus();
    }
}
