package models.enums;
/*
Explanation:
- we have commands in our profile menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Command {
    ShowInfo("show user info"),
    ChangeCurrency("\\s*change-currency\\s+-n\\s+(?<currency>.*)\\s*"),
    ChangeUsername("\\s*change-username\\s+-n\\s+(?<username>.*)\\s*"),
    ChangePassword("\\s*change-password\\s+" +
            "-o\\s+(?<oldPassword>.*)\\s+" +
            "-n\\s+(?<newPassword>.*)\\s*"),
    Back("\\s*back\\s*");

    private final String pattern;
    ProfileMenuCommands(String pattern) {
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
