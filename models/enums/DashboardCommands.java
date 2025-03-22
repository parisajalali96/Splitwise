package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our dashboard and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum DashboardCommands implements Command {
    Expense("\\d+"),
    GroupName("[a-zA-Z0-9!@#$%^&* ]{4,30}"),
    CreateGroup("create-group\\s+-n\\s+(?<name>.+)\\s+" +
            "-t\\s+(?<type>\\S+)"),
    ShowMyGroups("show my groups"),
    AddUserToGroup("add-user\\s+-u\\s+(?<username>\\S+)\\s+" +
            "-e\\s+(?<email>\\S+)\\s+" +
            "-g\\s+(?<id>\\d+)"),
    AddExpense("add-expense\\s+-g\\s+(?<groupId>\\d+)\\s+" +
            "-s\\s+(?<equality>\\S+)\\s+" +
            "-t\\s+(?<totalExpense>\\d+)\\s+" +
            "-n\\s+(?<numOfUsers>\\d+)"),
    ShowBalance("show balance\\s+-u\\s+(?<username>\\S+)"),
    SettleUp("settle-up\\s+-u\\s+(?<username>\\S+)\\s+" +
            "-m\\s+(?<money>\\d+)"),
    GoToProfileMenu("go to profile menu"),
    Logout("logout");

    private String pattern;
    DashboardCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input){
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);

        if(matcher.matches()){
            return matcher;
        }
        return null;
    }


}
