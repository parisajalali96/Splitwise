package views;

/*
Explanation:
- This is a view class for the SignUpMenu.
- This class should use to check inputs and print outputs for the SignUpMenu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */

import controllers.SignUpMenuController;
import models.enums.SignUpMenuCommands;

import java.util.regex.Matcher;

public class SignUpMenu implements AppMenu{
    final private SignUpMenuController controller = new SignUpMenuController();
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if((matcher = SignUpMenuCommands.Register.getMatcher(input)) != null) {
            controller.registerUser(matcher.group("username"),matcher.group("password"),
                    matcher.group("email"), matcher.group("name"));
        } else if((matcher = SignUpMenuCommands.GoToLogIn.getMatcher(input)) != null) {
            controller.goToLogin();
        }

    }

}
