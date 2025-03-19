package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our login menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum LoginMenuCommands implements Command {
    Login("login\\s+-u\\s+(?<username>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)"),
    ForgotPassword("forget-password\\s+-u\\s+(?<username>\\S+)\\s+" +
            "-e\\s+(?<email>\\S+)"),
    GoToSignUp("go to signup menu");

    private final String pattern;
    LoginMenuCommands(String pattern) {
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