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
    ChangeCurrency("change-currency\\s+-n\\s+(?<currency>(GTC|SUD|QTR))"),
    ChangeUsername("change-username\\s+-n\\s+(?<username>[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z])"),
    ChangePassword("change-password\\s+-o(?<oldPassword)(?<password>(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])\\S{6,12})\\s+" +
            "-n\\s+(?<newPassword>(?<password>(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])\\S{6,12})\\s+"),
    Back("back");

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
