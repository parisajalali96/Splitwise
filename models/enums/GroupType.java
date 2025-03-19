package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Explanation:
- In our app, groups have some types that are constants.
- In these cases, we use enums to define them and use them in our code.
- put those types here and use them in your code.
 */
public enum GroupType implements Command{
    Home("Home"),
    Trip("Trip"),
    ZanOBache("Zan-O-Bache"),
    Other("Other");

    private String type;
    GroupType(String type) {
        this.type = type;
    }

    @Override
    public Matcher getMatcher(String input){
        Matcher matcher = Pattern.compile(this.type).matcher(input);

        if(matcher.matches()){
            return matcher;
        }
        return null;
    }



}
