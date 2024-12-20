package ir.selab.tdd.service;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public boolean loginWithUsername(String username, String password) {
        User userByUsername = repository.getUserByUsername(username);
        if (userByUsername == null) {
            return false;
        }
        return userByUsername.getPassword().equals(password);
    }

    public boolean loginWithEmail(String email, String password) {
        User user = repository.getUserByEmail(email);
        if (user == null)
            return false;
        return user.getPassword().equals(password);
    }

    public boolean registerUser(String username, String password) {
        User user = new User(username, password);
        return repository.addUser(user);
    }

    public boolean registerUser(String username, String password, String email) {
        User user = new User(username, password);
        user.setEmail(email);
        return repository.addUser(user);
    }

    public boolean removeUser(String username) {
        return repository.removeUser(username);
    }

    public List<User> getAllUsers() {
        return repository.findAll().stream()
                .map(user -> new User(user.getUsername(), user.getPassword(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public boolean changeUserEmail(String username, String newEmail) {
        return repository.changeUserEmail(username, newEmail);
    }
}
