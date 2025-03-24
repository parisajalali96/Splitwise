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
    Expense("\\s*(?<expense>\\d+)\\s*"),
    UnequalExpense("(?<username>[a-zA-Z][a-zA-Z0-9._-]{3,9})\\s+(?<expense>\\d+\\s*\\S*)"),
    GroupName("[a-zA-Z0-9!@#$%^&* ]{4,30}"),
    CreateGroup("\\s*create-group\\s+-n\\s+(?<name>.+)\\s+" +
            "-t\\s+(?<type>.*)\\s*"),
    ShowMyGroups("\\s*show my groups\\s*"),
    AddUserToGroup("\\s*add-user\\s+-u\\s+(?<username>.*)\\s+" +
            "-e\\s+(?<email>.*)\\s+" +
            "-g\\s+(?<id>.*)\\s*"),
    AddExpense("\\s*add-expense\\s+-g\\s+(?<groupId>-?\\d+)\\s+" +
            "-s\\s+(?<equality>.*)\\s+" +
            "-t\\s+(?<totalExpense>.*)\\s+" +
            "-n\\s+(?<numOfUsers>.*)\\s*"),
    ShowBalance("\\s*show balance\\s+-u\\s+(?<username>\\S+)\\s*"),
    SettleUp("settle-up\\s+-u\\s+(?<username>.*)\\s+" +
            "-m\\s+(?<money>.*)\\s*"),
    GoToProfileMenu("\\s*go to profile menu\\s*"),
    Logout("\\s*logout\\s*");

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
