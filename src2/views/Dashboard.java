package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import controllers.DashboardController;
import models.enums.DashboardCommands;
import java.util.regex.Matcher;

public class Dashboard implements AppMenu {
    DashboardController controller = new DashboardController();
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if ((matcher = DashboardCommands.CreateGroup.getMatcher(input)) != null) {
            controller.createGroup(matcher.group("name"), matcher.group("type"));
        } else if ((matcher = DashboardCommands.ShowMyGroups.getMatcher(input)) != null) {
            controller.showMyGroups();
        } else if ((matcher = DashboardCommands.AddUserToGroup.getMatcher(input)) != null) {
            controller.addUserToGroup(matcher.group("username"),matcher.group("email"), matcher.group("id"));
        } else if ((matcher = DashboardCommands.AddExpense.getMatcher(input)) != null) {
            controller.addExpence(matcher.group("groupId"), matcher.group("equality"),
                    matcher.group("totalExpense"), matcher.group("numOfUsers"));
        } else if ((matcher = DashboardCommands.ShowBalance.getMatcher(input)) != null) {
            controller.showBalance(matcher.group("username"));

        } else if ((matcher = DashboardCommands.SettleUp.getMatcher(input)) != null) {
            controller.settleUp(matcher.group("username"), matcher.group("money"));

        } else if ((matcher = DashboardCommands.GoToProfileMenu.getMatcher(input)) != null) {
            controller.goToProfileMenu();

        } else if ((matcher = DashboardCommands.Logout.getMatcher(input)) != null) {
            controller.logout();
        } else controller.invalidCommand();

    }
}
