package views;

/*
Explanation:
- This is a view class for the App.
- our app needs a place that handle menus (:
 */

import models.App;
import models.enums.Menu;

import java.util.Scanner;

public class AppView {
    private static Scanner scanner = new Scanner(System.in);
    public void run() {
        String input;
        while (true) {
           input = scanner.nextLine();
           if (input.equals("exit")) break;
           App.getCurrentMenu().check(input);
           //if(App.getCurrentMenu() == Menu.ExitMenu) break;

        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

}
