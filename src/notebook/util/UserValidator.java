package notebook.util;
import java.util.Scanner;

public class UserValidator {

    public String validate(String msg) {
        System.out.print(msg);
        Scanner in = new Scanner(System.in);
        String field = in.next();
        if (field.isEmpty()) {
            throw new IllegalArgumentException("Введены не корректные данные");
        }
        field = field.replaceAll(" ", "").trim();
        return field;
    }
}
