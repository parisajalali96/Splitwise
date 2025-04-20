package models.enums;

import views.*;

/*
Explanation:
- In your code, you have some menus that are constants, and we move between them.
- a good way to handle this is to use enums to define them and use them in your code.
 */
public enum Menu {
    SignUpMenu(new SignUpMenu()),
    LoginMenu(new LoginMenu()),
    Dashboard(new Dashboard()),
    ProfileMenu(new ProfileMenu()),
    ExitMenu(new ExitMenu());

    private AppMenu menu;

    Menu (AppMenu menu) {
        this.menu = menu;
    }

    public void check (String input) {
        this.menu.check(input);
    }

}
