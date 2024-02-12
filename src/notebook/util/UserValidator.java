package notebook.util;

import notebook.model.User;

import java.util.Scanner;

public class UserValidator {

    public static String validate(String msg) {
        String field = prompt(msg);
        if (!field.isEmpty()) {
            throw new IllegalArgumentException("Введены не корректные данные");
        }
        user.setFirstName(user.getFirstName().replaceAll(" ", "").trim());
        user.setLastName(user.getLastName().replaceAll(" ", "").trim());
        user.setPhone(user.getPhone().replaceAll(" ", "").trim());
        return user;
    }

    private static String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
