package controllers;

import models.*;
import models.enums.DashboardCommands;
import models.enums.GroupType;
import models.enums.LoginMenuCommands;
import models.enums.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

/*
Explanation:
- This is a controller class for the dashboard Controller.
- This class will be used to implement functions that do dashboard operations.
- notice that this class should not have any input and output and just use it to implement functionalities.
 */
public class DashboardController {
    Matcher matcher = null;

    public Result createGroup(String name, String type) {
        if ((matcher = DashboardCommands.GroupName.getMatcher(name)) == null) {
            return new Result(false, "group name format is invalid!");
        } else if ((matcher = GroupType.Home.getMatcher(type)) == null && (matcher = GroupType.Trip.getMatcher(type)) == null &&
                (matcher = GroupType.ZanOBache.getMatcher(type)) == null && (matcher = GroupType.Other.getMatcher(type)) == null) {
            return new Result(false, "group type is invalid!");
        }
        Group group = new Group(name, type, App.getLoggedInUser());
        App.getGroups().add(group);
        return new Result(true, "group created successfully!");
    }

    public void showMyGroups() {
        for (Group group : App.getGroups()) {
            if(group.getCreator().equals(App.getLoggedInUser())) {
                System.out.println("group name : " + group.getName());
                System.out.println("id : " + group.getId());
                System.out.println("type : " + group.getType());
                System.out.println("creator : " + group.getCreator().getName());
                System.out.println("members : ");
                for (User user : group.getMembers()) {
                    System.out.println(user.getName());
                }
                System.out.println("--------------------");
            }
        }
    }

    public Result addUserToGroup(String username, String email, String id) {
        Matcher matcher = null;
        int groupId = Integer.parseInt(id);
        if(findUser(username) == null) {
            return new Result(false, "user not found!");
        } else if (findGroup(groupId) == null) {
            return new Result(false, "group not found!");
        } else if(findGroup(groupId).getMembers().contains(username)) {
            return new Result(false, "user already in the group!");
        } else if(!findUser(username).getEmail().equals(email)) {
            return new Result(false, "the email provided does not match the username!");
        } else if (!findGroup(groupId).getCreator().equals(App.getLoggedInUser())) {
            return new Result(false, "only the group creator can add users!");
        }
        findGroup(groupId).getMembers().add(findUser(username));
        return new Result(true, "user added to the group successfully!");
    }

    public Result addExpence (String id, String equality, String exp, String n ) {
        Scanner scanner = new Scanner(System.in);
        int groupId = Integer.parseInt(id);
        int expence = Integer.parseInt(exp);
        int num = Integer.parseInt(n);
        boolean equal = false;
        if(equality.equals("equally")) equal = true;
        if(findGroup(groupId) == null) {
            return new Result(false, "group not found!");
        } else {
            if(equal) {
                String username;
                ArrayList<User> expenseMembers = new ArrayList<>();
                if(DashboardCommands.Expense.getMatcher(exp) == null) {
                    return new Result(false, "expense format is invalid!");
                }
                for (int i = 0; i < num; i++) {
                    username = scanner.nextLine();
                    if(!findGroup(groupId).getMembers().contains(username)) {
                        return new Result(false, username + " not in group!");
                    }
                    expenseMembers.add(findUser(username));
                }
                scanner.close();
                for (User user : expenseMembers) {
                    Expense newExpence = new Expense (expence/num, groupId, user, App.getLoggedInUser());
                    App.getExpenses().add(newExpence);
                }
                return new Result(true, "expense added successfully!");
            } else {
                HashMap<User, Integer> expenses = new HashMap<>();
                String username;
                String expense;
                int expenseNumber = 0;
                for(int i = 0; i < num; i++) {
                    username = scanner.next();
                    expense = scanner.next();
                    if(!findGroup(groupId).getMembers().contains(username)) {
                        return new Result(false, username + " not in group!");
                    } else if(DashboardCommands.Expense.getMatcher(expense) == null) {
                        return new Result(false, "expense format is invalid!");
                    }
                    expenseNumber = Integer.parseInt(expense);
                    expenses.put(findUser(username), expenseNumber);
                }
                for(User user : expenses.keySet()) {
                    Expense newExpense = new Expense (expenses.get(user), groupId, user, App.getLoggedInUser());
                    App.getExpenses().add(newExpense);
                }
                return new Result(true, "expense added successfully!");
            }
        }
    }

    public Result goToProfileMenu() {
        App.setCurrentMenu(Menu.ProfileMenu);
        return new Result(true, "you are now in profile menu!");
    }
    public Group findGroup(int groupId) {
        for (Group group : App.getGroups()) {
            if (group.getId() == groupId) {
                return group;
            }
        }
        return null;
    }
    public User findUser(String username) {
        for (User user : App.getUsers()) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}