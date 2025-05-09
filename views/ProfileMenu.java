package views;
/*
Explanation: 
- This is a view class for profile menu.
- This class should use to check inputs and print outputs for profile menu.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.ProfileMenuController;
import models.enums.ProfileMenuCommands;

import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu{
    ProfileMenuController controller = new ProfileMenuController();
    @Override
    public void check(String input) {
        Matcher matcher = null;

        if((matcher = ProfileMenuCommands.ShowInfo.getMatcher(input)) != null) {
            controller.showUserInfo();
        } else if((matcher = ProfileMenuCommands.ChangeCurrency.getMatcher(input)) != null) {
            controller.changeCurrency(matcher.group("currency"));
        } else if((matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null) {
            controller.changeUsername(matcher.group("username"));
        } else if((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null) {
            controller.changePassword(matcher.group("oldPassword"), matcher.group("newPassword"));
        } else if((matcher = ProfileMenuCommands.Back.getMatcher(input)) != null) {
           controller.back();
        } else if (!input.isEmpty()) {
            controller.invalidCommand();
        }
    }

}
