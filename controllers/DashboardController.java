package controllers;

import models.*;
import models.enums.DashboardCommands;
import models.enums.GroupType;
import models.enums.Menu;
import views.AppView;

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
        name = name.trim();
        type = type.trim();
        if ((matcher = DashboardCommands.GroupName.getMatcher(name)) == null) {
            return new Result(false, "group name format is invalid!");
        } else if ((matcher = GroupType.Home.getMatcher(type)) == null && (matcher = GroupType.Trip.getMatcher(type)) == null &&
                (matcher = GroupType.ZanOBache.getMatcher(type)) == null && (matcher = GroupType.Other.getMatcher(type)) == null) {
            return new Result(false, "group type is invalid!");
        }
        Group group = new Group(name, type, App.getLoggedInUser());
        App.getGroups().add(group);
        App.getLoggedInUser().addGroup(group);
        return new Result(true, "group created successfully!");
    }

    public void showMyGroups() {
        boolean groupFound = false;
        for (Group group : App.getLoggedInUser().getGroups()) {
                System.out.println("group name : " + group.getName());
                System.out.println("id : " + group.getId());
                System.out.println("type : " + group.getType());
                System.out.println("creator : " + group.getCreator().getName());
                System.out.print("members : \n");
                for (User user : group.getMembers()) {
                    System.out.println(user.getName());
                }
                System.out.println("--------------------");
                groupFound = true;
        }
        if(!groupFound) {
            System.out.println("");
        }
    }

    public Result addUserToGroup(String username, String email, String id) {
        Matcher matcher = null;
        int groupId = Integer.parseInt(id);
        username = username.trim();
        email = email.trim();
        if(findUser(username) == null) {
            return new Result(false, "user not found!");
        } else if (findGroup(groupId) == null) {
            return new Result(false, "group not found!");
        } else if(findGroup(groupId).getMembers().contains(findUser(username))) {
            return new Result(false, "user already in the group!");
        } else if(!findUser(username).getEmail().equals(email)) {
            return new Result(false, "the email provided does not match the username!");
        } else if (!findGroup(groupId).getCreator().equals(App.getLoggedInUser())) {
            return new Result(false, "only the group creator can add users!");
        }
        findGroup(groupId).getMembers().add(findUser(username));
        findUser(username).getGroups().add(findGroup(groupId));
        return new Result(true, "user added to the group successfully!");
    }

    public Result addExpence (String id, String equality, String exp, String n ) {
        Scanner scanner = AppView.getScanner();
        int groupId = Integer.parseInt(id);
        int expence;
        int num = Integer.parseInt(n);
        boolean equal = equality.equals("equally");
        if(findGroup(groupId) == null) {
            System.out.println("group not found!");
            for(int i = 0; i < num; i++) {
                scanner.nextLine();
            }
            return new Result(false, "");
        } else {
            if(equal) {
                String username;
                ArrayList<User> expenseMembers = new ArrayList<>();
                boolean found = true;
                for (int i = 0; i < num; i++) {
                    username = scanner.nextLine().trim();
                    if(!findGroup(groupId).getMembers().contains(findUser(username))) {
                        System.out.println(username + " not in group!");
                        found = false;
                    }
                    if(found && !username.equals(App.getLoggedInUser().getUsername())) expenseMembers.add(findUser(username));
                }
                if(!found) return new Result(false, "");
                if(DashboardCommands.Expense.getMatcher(exp) == null) {
                    return new Result(false, "expense format is invalid!");
                }
                expence = Integer.parseInt(DashboardCommands.Expense.getMatcher(exp).group("expense"));
                for (User user : expenseMembers) {
                    if(findExpense(user) == null) {
                        Expense newExpence = new Expense((expence / num) / App.getLoggedInUser().getCurrency().getCurrencyValue(), groupId, user, App.getLoggedInUser());
                        App.getExpenses().add(newExpence);
                    } else {
                        Expense expense = findExpense(user);
                        if(expense.getOutDebt().getUsername().equals(App.getLoggedInUser().getUsername())) {
                            expense.increaseExpense((expence / num) / App.getLoggedInUser().getCurrency().getCurrencyValue());
                        } else {
                            expense.increaseExpense(-1*(expence / num) / App.getLoggedInUser().getCurrency().getCurrencyValue());
                        }
                    }
                }
                return new Result(true, "expense added successfully!");
            } else {
                HashMap<User, Integer> expenses = new HashMap<>();
                String username;
                String expense;
                int expenseNumber = 0;
                int expenseT = 0;
                int expenseTotal;
                boolean found = true;
                boolean expenseValid = true;
                boolean expenseError = false;
                for(int i = 0; i < num; i++) {
                    String input = scanner.nextLine();
                    username = DashboardCommands.UnequalExpense.getMatcher(input).group("username");
                    expense = DashboardCommands.UnequalExpense.getMatcher(input).group("expense");
                    if (!findGroup(groupId).getMembers().contains(findUser(username)) && !expenseError) {
                        System.out.println(username.trim() + " not in group!");
                        found = false;

                    } else if (DashboardCommands.Expense.getMatcher(expense) == null) {
                        expenseValid = false;
                    }
                    if(!expenseValid && found && !expenseError) {
                        System.out.println("expense format is invalid!");
                        expenseError = true;
                    }
                    if(!found || !expenseValid) continue;
                    expenseNumber = Integer.parseInt(DashboardCommands.Expense.getMatcher(expense).group("expense"));
                    expenseT += expenseNumber;
                    if (found && expenseValid && !username.equals(App.getLoggedInUser().getUsername())) {
                        expenses.put(findUser(username), expenseNumber);
                    }
                }

                if(!found || !expenseValid) return new Result(false, "");
                if(DashboardCommands.Expense.getMatcher(exp) == null) {
                    return new Result(false, "expense format is invalid!");
                }
                expence = Integer.parseInt(DashboardCommands.Expense.getMatcher(exp).group("expense"));


                if(expenseT != expence) return new Result (false, "the sum of individual costs does not equal the total cost!");
                for(User user : expenses.keySet()) {
                    if(findExpense(user) == null) {
                        Expense newExpense = new Expense(expenses.get(user) / App.getLoggedInUser().getCurrency().getCurrencyValue(), groupId, user, App.getLoggedInUser());
                        App.getExpenses().add(newExpense);
                    } else {
                        Expense newExpense = findExpense(user);
                        if(newExpense.getOutDebt().getUsername().equals(App.getLoggedInUser().getUsername())) {
                            newExpense.increaseExpense(expenses.get(user) / App.getLoggedInUser().getCurrency().getCurrencyValue());
                        } else {
                            newExpense.increaseExpense(-1*(expenses.get(user) / App.getLoggedInUser().getCurrency().getCurrencyValue()));
                        }
                    }
                }
                return new Result(true, "expense added successfully!");
            }
        }
    }

    public int totalBalance (User user) {
        int totalBalance = 0;
        Expense expense = findExpense(user);
        if(expense == null) return 0;

        if(expense.getOutDebt().getUsername().equals(App.getLoggedInUser().getUsername())) {
            return expense.getExpense();
        } else return -1*expense.getExpense();
    }

    public Result showBalance(String username) {
        username = username.trim();
        User user = findUser(username);
        int totalBalance = 0;
        if(user == null) {
            return new Result(false, "user not found!");
        } else if (getMututalGroups(user).isEmpty()) return new Result (true, "you are settled with " + user.getUsername());

        totalBalance = totalBalance(user);
        if (totalBalance > 0) {
            return new Result(true, user.getUsername() + " owes you " +
                    totalBalance*App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " in " + getMututalGroups(user));
        } else if (totalBalance == 0) {
            return new Result (true, "you are settled with " + user.getUsername());
        } else {
            totalBalance *= -1;
            return new Result(true, "you owe " + user.getUsername() + " " +
                    totalBalance*App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " in " + getMututalGroups(user));
        }
    }

    public Result settleUp(String username, String money) {
        username = username.trim();
        User user = findUser(username);
        int moneyInput;
        int debtTotal = 0;
        if(user == null) {
            return new Result(false, "user not found!");
        } else if (DashboardCommands.Expense.getMatcher(money) == null) {
            return new Result(false, "input money format is invalid!");
        } else if(App.getLoggedInUser().getUsername().equals(username) || getMututalGroups(user).isEmpty()) {
            return new Result(true, "you are settled with " + user.getUsername() + " now!");
        }

        moneyInput = Integer.parseInt(DashboardCommands.Expense.getMatcher(money).group("expense"))/App.getLoggedInUser().getCurrency().getCurrencyValue();

        Expense expense = findExpense(user);
        if(expense == null) {
            expense = new Expense(moneyInput, 1, user, App.getLoggedInUser());
            App.getExpenses().add(expense);
            return new Result(true, user.getUsername() + " owes you " +
                    moneyInput*App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " now!");
        }
        debtTotal = -1*totalBalance(user);

        if (debtTotal > 0) {
            if(debtTotal - moneyInput > 0) {
                debtTotal -= moneyInput;
                expense.setExpense(debtTotal);

                return new Result(true, "you owe " + user.getUsername() + " " +
                        debtTotal*App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                        App.getLoggedInUser().getCurrency().toString() + " now!");
            } else if (debtTotal - moneyInput == 0) {
                expense.setExpense(0);
                return new Result(true, "you are settled with " + user.getUsername() + " now!");
            } else {
                debtTotal -= moneyInput;
                expense.setExpense(-1*debtTotal);
                expense.setOutDebt(App.getLoggedInUser());
                expense.setInDebt(user);
                return new Result(true, user.getUsername() + " owes you " +
                        -1*debtTotal*App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                        App.getLoggedInUser().getCurrency().toString() + " now!");
            }
        } else {
            debtTotal -= moneyInput;
            expense.setExpense(-1*debtTotal);

            return new Result(true, user.getUsername() + " owes you " +
                    -1*debtTotal*App.getLoggedInUser().getCurrency().getCurrencyValue() + " " +
                    App.getLoggedInUser().getCurrency().toString() + " now!");
        }

    }

    public String getMututalGroups(User user) {
        StringBuilder groups = new StringBuilder();
        for (Group group : App.getGroups()) {
            if(group.getMembers().contains(user) && group.getMembers().contains(App.getLoggedInUser())) {
                groups.append(group.getName()).append(", ");
            }
        }
        if(!groups.toString().equals("")) {
            groups = new StringBuilder(groups.substring(0, groups.length() - 2));
            groups.append("!");
        }
        return groups.toString();
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

    public Expense findExpense(User user) {
        for (Expense expense : App.getExpenses()) {
            if((expense.getInDebt().getUsername().equals(user.getUsername())
                    && expense.getOutDebt().getUsername().equals(App.getLoggedInUser().getUsername()))
                    || (expense.getInDebt().getUsername().equals(App.getLoggedInUser().getUsername()) &&
                    expense.getOutDebt().getUsername().equals(user.getUsername()))) {
                return expense;
            }
        }
        return null;
    }

}