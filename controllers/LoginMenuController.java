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
        if(findUser(username) == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!findUser(username).getPassword().equals(password)) {
            return new Result(false, "password is incorrect!");
        }
        App.setLoggedInUser(findUser(username));
        App.setCurrentMenu(Menu.Dashboard);
        return new Result(true, "user logged in successfully.you are now in dashboard!");
    }

    public Result forgotPassword(String username, String email) {
        if(findUser(username) == null) {
            return new Result(false, "username doesn't exist!");
        } else if (!findUser(username).getEmail().equals(email)) {
            return new Result(false, "email doesn't match!");
        }
        return new Result(true, "password : " + findUser(username).getPassword());
    }

    public Result goToSignup() {
        App.setCurrentMenu(Menu.SignUpMenu);
        return new Result(true, "you are now in signup menu!");
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
