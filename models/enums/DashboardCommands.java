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
    CreateGroup("create-group\\s+-n\\s+(?<name>[a-zA-Z0-9!@#$%^&*]{4,30})\\s+" +
            "-t(?<type>(Home|Trip|Zan-O-Bache|Other))"),
    ShowMyGroups("show my groups"),
    AddUserToGroup("add-user\\s+-u\\s+(?<username>[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z])" +
            "-e\\s+(?<email>[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z]@[a-zA-Z0-9.-]+\\.(com|net|edu|org))" +
            "-g\\s+(?<id>\\d+)"),
    AddExpense(""), //TODO
    ShowBalance("show balance\\s+-u\\+s(?<username>[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z])"),
    SettleUp("settle-up\\s+-u\\s+(?<username>[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z])" +
            "-m(?<money>\\d+)"),
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
