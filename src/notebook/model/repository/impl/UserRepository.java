package notebook.model.repository.impl;

import notebook.util.DBConnector;
import notebook.util.mapper.impl.UserMapper;
import notebook.model.User;
import notebook.model.repository.GBRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements GBRepository {
    private final UserMapper mapper;
    List<User> allUsers = new ArrayList<>();

    public UserRepository() {
        this.mapper = new UserMapper();
        allUsers = loadAll();
    }

    private List<User> loadAll() {
        List<String> lines = readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            if (!line.isEmpty()) {
                users.add(mapper.toOutput(line));
            }
        }
        return users;
    }

    @Override
    public List<User> findAll() {
        return allUsers;
    }

    @Override
    public User create(User user) {
        long max = 0L;
        for (User u : allUsers) {
            long id = u.getId();
            if (max < id){
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        allUsers.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User userById = allUsers.stream()
                .filter(u -> u.getId()
                        .equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("User not found"));
        return Optional.of(userById);
    }

    @Override
    public Optional<User> update(Long userId, User update) {
        User editUser = allUsers.stream()
                .filter(u -> u.getId()
                        .equals(userId))
                .findFirst().orElseThrow(() -> new RuntimeException("User not found"));

        if(!update.getFirstName().isEmpty()) {
            editUser.setFirstName(update.getFirstName());
        }
        if(!update.getLastName().isEmpty()) {
            editUser.setLastName(update.getLastName());
        }
        if(!update.getPhone().isEmpty()) {
            editUser.setPhone(update.getPhone());
        }
        return Optional.of(update);
    }

    @Override
    public boolean delete(Long id) {
        User removeUser = allUsers.stream()
                .filter(u -> u.getId()
                        .equals(id))
                .findFirst().orElse(null);
        if (removeUser != null) {
            allUsers.remove(removeUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(DBConnector.DB_PATH);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(DBConnector.DB_PATH, false)) {
            for (String line : data) {
                // запись всей строки
                writer.write(line);
                // запись по символам
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeAll() {
        List<String> lines = new ArrayList<>();
        for (User u: allUsers) {
            lines.add(mapper.toInput(u));
        }
        saveAll(lines);
    }
}
