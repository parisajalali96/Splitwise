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
    UsernameRegex("[a-zA-Z][a-zA-Z0-9._-]{3,9}"),
    PasswordRegex("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z\\d!@#$%^&*]{6,12}"),
    EmailRegex("^[A-Za-z][\\w.-]{3,9}@[a-z](?![a-z]*[-.][a-z]*[-.][a-z]*[a-z]\\.)[a-z.\\-]{1,5}[a-z]\\.(org|com|net|edu)$"),
    NameRegex("[A-Za-z]([A-Za-z-]*[A-Za-z])*"),
    Register("\\s*register\\s+-u\\s+(?<username>.+?)\\s+" +
            "-p\\s+(?<password>.+?)\\s+" +
            "-e\\s+(?<email>.+?)\\s+" +
            "-n\\s+(?<name>.+?)\\s*"),
    GoToLogIn("\\s*go to login menu\\s*");

    final private String pattern;
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
