package models;

import models.enums.Menu;

import java.util.ArrayList;

/*
Explanation:
- In out app, we need somewhere to keep our general data like list of users and list of groups and logged-in user etc.
- This class is the place for that.
- Put your general data here and use them in your code.
- you should put some functions here to manage your data too.
 */
public class App {
    private static final User loggedInUser = null;
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Group> groups = new ArrayList<>();
    private static final ArrayList<Expense> expenses = new ArrayList<>();
    private static final Menu currentMenu = Menu.SignUpMenu;


}
