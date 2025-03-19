package views;
/*
Explanation:
- This is a view class for the login menu.
- This class should use to check inputs and print outputs for the login menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.LoginMenuController;
import models.enums.LoginMenuCommands;
import models.enums.SignUpMenuCommands;

import java.util.regex.Matcher;

public class LoginMenu implements AppMenu{
    LoginMenuController controller = new LoginMenuController();
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            controller.loginUser(matcher.group("username"), matcher.group("password"));
        } else if((matcher = LoginMenuCommands.ForgotPassword.getMatcher(input)) != null) {
            controller.forgotPassword(matcher.group("username"), matcher.group("email"));
        } else if((matcher = LoginMenuCommands.GoToSignUp.getMatcher(input)) != null) {
            controller.goToSignup();
        }
    }
}
