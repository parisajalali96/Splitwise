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
    UnequalExpense("\\s*(?<username>\\S+(\\s*\\W+)*)\\s+(?<expense>\\d+\\s*\\S*)\\s*"),
    GroupName("[a-zA-Z0-9!@#$%^&* ]{4,30}"),
    CreateGroup("\\s*create-group\\s+-n\\s+(?<name>\\S+(\\s*\\S+)*)\\s+" +
            "-t\\s+(?<type>\\S+(\\s*\\S+)*)\\s*"),
    ShowMyGroups("\\s*show my groups\\s*"),
    AddUserToGroup("\\s*add-user\\s+-u\\s+(?<username>\\S+(\\s*\\S+)*)\\s+" +
            "-e\\s+(?<email>\\S+(\\s*\\S+)*)\\s+" +
            "-g\\s+(?<id>\\S+(\\s*\\S+)*)\\s*"),
    AddExpense("\\s*add-expense\\s+-g\\s+(?<groupId>-?\\d+)\\s+" +
            "-s\\s+(?<equality>\\S+(\\s*\\S+)*)\\s+" +
            "-t\\s+(?<totalExpense>\\S+(\\s*\\S+)*)\\s+" +
            "-n\\s+(?<numOfUsers>\\d+)\\s*"),
    ShowBalance("\\s*show balance\\s+-u\\s+(?<username>\\S+(\\s*\\S+)*)\\s*"),
    SettleUp("\\s*settle-up\\s+-u\\s+(?<username>\\S+(\\s*\\S+)*)\\s+" +
            "-m\\s+(?<money>\\S+(\\s*\\S+)*)\\s*"),
    GoToProfileMenu("\\s*go to profile menu\\s*"),
    Logout("\\s*logout\\s*");

    private final String pattern;
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
