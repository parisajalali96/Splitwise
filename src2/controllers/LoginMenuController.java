package controllers;
/*
Explanation:
- This is a controller class for the login menu Controller.
- This class will be used to implement functions that do log in menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;

import java.util.regex.Matcher;

public class LoginMenuController {
    Matcher matcher = null;
    public Result loginUser (String username, String password) {
        User user = findUser(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!passwordMatches(user, password)) {
            return new Result(false, "password is incorrect!");
        }
        App.setLoggedInUser(user);
        App.setCurrentMenu(Menu.Dashboard);
        return new Result(true, "user logged in successfully.you are now in dashboard!");
    }

    public boolean passwordMatches (User user, String password) {
        return user.getPassword().equals(password);
    }
    public Result forgotPassword(String username, String email) {
        User user = findUser(username);
        if(user == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!emailMatches(user, email)) {
            return new Result(false, "email doesn't match!");
        }
        return new Result(true, "password : " + findUser(username).getPassword());
    }

    public boolean emailMatches (User user, String email) {
        return user.getEmail().equals(email);
    }
    public Result goToSignup() {
        App.setCurrentMenu(Menu.SignUpMenu);
        return new Result(true, "you are now in signup menu!");
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command!");
    }

    public User findUser (String username) {
        for(User user : App.getUsers()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
