package controllers;
/*
Explanation:
- This is a controller class for the sign-up menu Controller.
- This class will be used to implement functions that do sign up menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Menu;
import models.enums.SignUpMenuCommands;

import java.util.regex.Matcher;

public class SignUpMenuController {
    public Result registerUser(String username, String password, String email, String name) {
        Matcher matcher = null;
        //boolean valid = true;
        if ((matcher = SignUpMenuCommands.UsernameRegex.getMatcher(username)) == null) {
            return new Result(false, "username format is invalid!");
        } else if (App.getUsers().contains(username)) {
            return new Result(false, "this username is already taken!");
        } else if ((matcher = SignUpMenuCommands.PasswordRegex.getMatcher(password)) == null) {
            return new Result(false, "password format is invalid!");
        } else if ((matcher = SignUpMenuCommands.EmailRegex.getMatcher(email)) == null) {
            return new Result(false, "email format is invalid!");
        } else if ((matcher = SignUpMenuCommands.NameRegex.getMatcher(name)) == null) {
            return new Result(false, "name format is invalid!");
        }
        User newUser = new User(username, password, email, name);
        App.getUsers().add(newUser);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "user registered successfully.you are now in login menu!");
    }

    public Result goToLogin() {
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "you are now in login menu!");
    }
}
