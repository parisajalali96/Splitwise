package views;
/*
Explanation:
- This is a view class for the ExitMenu.
- We will just use it to end the program.
 */

import models.App;
import models.enums.Menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExitMenu implements AppMenu{
    @Override
    public void check(String input) {
        if(input.equals("exit")) {
            App.setCurrentMenu(Menu.ExitMenu);
        }
    }
}
