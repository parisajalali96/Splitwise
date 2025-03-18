package views;
/*
Explanation:
- This is a view class for the dashboard.
- This class should use to check inputs and print outputs for the dashboard.
- notice that : this class should not have any logic and just use it to get inputs and handle it to use correct methods in controller.
 */


import models.enums.DashboardCommands;
import models.enums.LoginMenuCommands;

import java.util.regex.Matcher;

public class Dashboard implements AppMenu {
    @Override
    public void check(String input) {
        Matcher matcher = null;
        if ((matcher = DashboardCommands.CreateGroup.getMatcher(input)) != null) {
            //TODO

        } else if ((matcher = DashboardCommands.ShowMyGroups.getMatcher(input)) != null) {
            //TODO

        } else if ((matcher = DashboardCommands.AddUserToGroup.getMatcher(input)) != null) {
            //TODO

        } else if ((matcher = DashboardCommands.AddExpense.getMatcher(input)) != null) {
            //TODO

        } else if ((matcher = DashboardCommands.ShowBalance.getMatcher(input)) != null) {
            //TODO

        } else if ((matcher = DashboardCommands.SettleUp.getMatcher(input)) != null) {
            //TODO

        } else if ((matcher = DashboardCommands.GoToProfileMenu.getMatcher(input)) != null) {
            //TODO

        } else if ((matcher = DashboardCommands.Logout.getMatcher(input)) != null) {
            //TODO

        }
    }


}
