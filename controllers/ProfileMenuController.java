package controllers;
/*
Explanation:
- This is a controller class for the profile menu Controller.
- This class will be used to implement functions that do profile menu operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */

import models.App;
import models.Result;
import models.User;
import models.enums.Currency;
import models.enums.Menu;
import models.enums.SignUpMenuCommands;

import java.util.regex.Matcher;

public class ProfileMenuController {
    Matcher matcher = null;

    public void showUserInfo() {
        User currentUser = App.getLoggedInUser();
        System.out.println("username : " + currentUser.getUsername());
        System.out.println("password : " + currentUser.getPassword());
        System.out.println("currency : " + currentUser.getCurrency().toString());
        System.out.println("email : " + currentUser.getEmail());
        System.out.println("name : " + currentUser.getName());
    }

    public Result changeCurrency (String currency) {
        if (Currency.matcher(currency) == null) {
            return new Result(false, "currency format is invalid!");
        }
        App.getLoggedInUser().setCurrency(Currency.matcher(currency));
        return new Result(true, "your currency changed to " + currency + " successfully!");
    }

    public Result changeUsername (String newUsername) {
        if (App.getLoggedInUser().getUsername().equals(newUsername)) {
            return new Result(false, "please enter a new username!");
        } else if (App.getUsers().contains(newUsername)) {
            return new Result(false, "this username is already taken!");
        } else if (SignUpMenuCommands.UsernameRegex.getMatcher(newUsername) == null) {
            return new Result(false, "new username format is invalid!");
        }
        App.getLoggedInUser().setUsername(newUsername);
        return new Result(true, "your username changed to " + newUsername + " successfully!");
    }

    public Result changePassword (String oldPassword, String newPassword) {
        if(!App.getLoggedInUser().getPassword().equals(oldPassword)) {
            return new Result(false, "password incorrect!");
        } else if (App.getLoggedInUser().getPassword().equals(newPassword)) {
            return new Result(false, "please enter a new password!");
        } else if (SignUpMenuCommands.PasswordRegex.getMatcher(newPassword) == null) {
            return new Result(false, "new password format is invalid!");
        }
        App.getLoggedInUser().setPassword(newPassword);
        return new Result(true, "your password changed successfully!");
    }

    public Result back () {
        App.setCurrentMenu(Menu.Dashboard);
        return new Result(true, "you are now in dashboard!");
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command!");
    }
}
