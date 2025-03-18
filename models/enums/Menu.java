package models.enums;

import views.AppMenu;

/*
Explanation:
- In your code, you have some menus that are constants, and we move between them.
- a good way to handle this is to use enums to define them and use them in your code.
 */
public enum Menu {
    SignUpMenu,
    LoginMenu,
    Dashboard,
    ProfileMenu;

    private AppMenu instance;


}
