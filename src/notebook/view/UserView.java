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
            com = Commands.valueOf(command);
            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE:
                    User u = userController.createUser();
                    userController.saveUser(u);
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
                    userController.updateUser(id, userController.createUser());
                    break;
                case DELETE:
                    id = validator.validate("Идентификатор пользователя: ");
                    userController.delete(id);
            }
        }
    }
}
