package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;
import notebook.util.UserValidator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserController {
    private final GBRepository repository;

    public UserController(GBRepository repository) {
        this.repository = repository;
    }

    public User createUser() {
        UserValidator validator = new UserValidator();
        String firstName = validator.validate("Имя: ");
        String lastName = validator.validate("Фамилия: ");
        String phone = validator.validate("Номер телефона: ");
        return new User(firstName, lastName, phone);
    }

    public boolean saveUser(User user) {
        return repository.create(user) != null;
    }

    public User readUser(Long userId) throws Exception {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("User not found");
    }

    public List<User> readAll() {
        return repository.findAll();
    }

    public Optional<User> updateUser(String userId, User update) {
        update.setId(Long.parseLong(userId));
        return repository.update(Long.parseLong(userId), update);
    }

    public boolean delete(String id) {
        return repository.delete(Long.parseLong(id));
    }
}
