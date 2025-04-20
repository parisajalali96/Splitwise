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
    private static  User loggedInUser = null;
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Group> groups = new ArrayList<>();
    private static final ArrayList<Expense> expenses = new ArrayList<>();
    private static  Menu currentMenu = Menu.SignUpMenu;

    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }
    public static ArrayList<User> getUsers() {
        return users;
    }
    public static ArrayList<Group> getGroups() {
        return groups;
    }
    public static ArrayList<Expense> getExpenses() {
        return expenses;
    }
}
