package controllers;

import models.*;
import models.enums.DashboardCommands;
import models.enums.GroupType;
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
                    if(!findGroup(groupId).getMembers().contains(findUser(username))) { //prob not correct
                        return new Result(false, username + " not in group!");
                    }
                    expenseMembers.add(findUser(username));
                }
                //scanner.close();
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
                int expenseT = 0;
                for(int i = 0; i < num; i++) {
                    username = scanner.next();
                    expense = scanner.next();
                    if(!findGroup(groupId).getMembers().contains(username)) {
                        return new Result(false, username + " not in group!");
                    } else if(DashboardCommands.Expense.getMatcher(expense) == null) {
                        return new Result(false, "expense format is invalid!");
                    }
                    expenseNumber = Integer.parseInt(expense);
                    expenseT += expenseNumber;
                    expenses.put(findUser(username), expenseNumber);
                }
                if(expenseT != expence) return new Result (false, "the sum of individual costs does not equal the total cost!");
                for(User user : expenses.keySet()) {
                    Expense newExpense = new Expense (expenses.get(user), groupId, user, App.getLoggedInUser());
                    App.getExpenses().add(newExpense);
                }
                return new Result(true, "expense added successfully!");
            }
        }
    }

    public int totalBalance (User user) {
        int totalBalance = 0;
        for (Expense expense : App.getExpenses()) {
            if(expense.getInDebt().getUsername().equals(App.getLoggedInUser().getUsername()) &&
                    expense.getOutDebt().getUsername().equals(user.getUsername())) {
                totalBalance += expense.getExpense();
            } else if (expense.getInDebt().getUsername().equals(user.getUsername()) &&
                    expense.getOutDebt().getUsername().equals(App.getLoggedInUser().getUsername())) {
                totalBalance -= expense.getExpense();
            }
        }
        return totalBalance;
    }
    public Result showBalance(String username) {
        User user = findUser(username);
        int totalBalance = 0;
        if(user == null) {
            return new Result(false, "user not found!");
        }
        totalBalance = totalBalance(user);
        if (totalBalance > 0) {
            return new Result(true, "you owe " + user.getUsername() + " " +
                    totalBalance/App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " in " + getMututalGroups(user));
        } else if (totalBalance == 0) {
            return new Result (true, "you are settled with " + user.getUsername());
        } else {
            totalBalance *= -1;
            return new Result(true, user.getUsername() + " owes you " +
                    totalBalance/App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " in " + getMututalGroups(user));
        }
    }
    public Result settleUp(String username, String money) {
        User user = findUser(username);
        int moneyInput;
        int debt = 0;
        if(user == null) {
            return new Result(false, "user not found!");
        } else if (DashboardCommands.Expense.getMatcher(money) == null) {
            return new Result(false, "input money format is invalid!");
        }
        moneyInput = Integer.parseInt(money);

        for(Expense expense : App.getExpenses()) {
            if(moneyInput == 0) break;
            if(expense.getInDebt().getUsername().equals(App.getLoggedInUser().getUsername()) &&
                    expense.getOutDebt().getUsername().equals(user.getUsername())) {
                debt = expense.getExpense();
                if(debt - moneyInput < 0) {
                    debt -=moneyInput;
                    expense.setOutDebt(App.getLoggedInUser());
                    expense.setInDebt(user);
                    expense.setExpense(-1*debt);
                    moneyInput -= debt;
                } else if (debt - moneyInput > 0) {
                    debt -= moneyInput;
                    expense.setExpense(debt);
                    moneyInput = 0;
                } else if (debt - moneyInput == 0) {
                    debt = 0;
                    expense.setExpense(debt);
                    moneyInput = 0;
                }
            }
        }

        if (debt == 0) {
            return new Result(true, "you are settled with " + user.getUsername() + " now!");
        } else if (debt > 0) {
            return new Result(true, "you owe " + user.getUsername() + " " +
                    debt/App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " now!");
        } else {
            return new Result(true, user.getUsername() + " owes you " +
                    -1*debt/App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " now!");
        }

    }
    public String getMututalGroups(User user) {
        String groups = "";
        for (Group group : App.getGroups()) {
            if(group.getMembers().contains(user) && group.getMembers().contains(App.getLoggedInUser())) {
                groups += "\"" + group.getName() + "\"" + ", ";
            }
        }
        groups = groups.substring(0, groups.length() - 2);
        groups += "!";
        return groups;
    }

    public Result goToProfileMenu() {
        App.setCurrentMenu(Menu.ProfileMenu);
        return new Result(true, "you are now in profile menu!");
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command!");
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

    public Result logout() {
        App.setLoggedInUser(null);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "user logged out successfully.you are now in login menu!");
    }

}