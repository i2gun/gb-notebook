package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;
import notebook.util.UserValidator;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;
        String id;
        UserValidator validator = new UserValidator();

        while (true) {
            String command = validator.validate("Введите команду: ");
            try {
                com = Commands.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Введена неверная команда");
                continue;
            }
            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE:
                    checkAndMessage(userController.saveUser(userController.createUser()),
                            "Добавление нового", "<>");
                    break;
                case READ:
                    id = validator.validate("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case LIST:
                    System.out.println(userController.readAll());
                    break;
                case UPDATE:
                    id = validator.validate("Enter user id: ");
                    checkAndMessage(userController.updateUser(id, userController.createUser()).isPresent(),
                            "Обновление", id);
                    break;
                case DELETE:
                    id = validator.validate("Идентификатор пользователя: ");
                    checkAndMessage(userController.delete(id), "Удаление", id);
            }
        }
    }

    private void checkAndMessage(boolean check, String msg, String id) {
        msg += " пользователя с ID " + id;
        if (check) {
            System.out.println(msg + " выполнено успешно");
        } else {
            System.out.println(msg + " не выполнено");
        }
    }
}
