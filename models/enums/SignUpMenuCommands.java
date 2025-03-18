package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- we have commands in our sign-up menu and this commands need regexes to be checked.
- put those regexes here and use them in your code.
- this regexes need some functions, put those functions in here.
 */
public enum SignUpMenuCommands implements Command {
    Register("register\\s+-u\\s+(?<username>[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z])\\s+" +
            "-p\\s+(?<password>(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])\\S{6,12})\\s+" +
            "-e\\s+(?<email>[a-zA-Z][a-zA-Z0-9._-]{2,8}[a-zA-Z]@[a-zA-Z0-9.-]+\\.(com|net|edu|org))"),
    GoToLogIn("go to login menu");

    final String pattern;
    SignUpMenuCommands(String pattern){
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
