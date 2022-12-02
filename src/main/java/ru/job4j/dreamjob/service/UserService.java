package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.persistence.UserDBStore;

import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class UserService {

    private final UserDBStore userDBStore;

    public UserService(UserDBStore userDBStore) {
        this.userDBStore = userDBStore;
    }

    public List<User> findAll() {
        return userDBStore.findAll();
    }

    public Optional<User> add(User user) {
        return Optional.ofNullable(userDBStore.add(user));
    }

    public User findUserById(int id) {
        return userDBStore.findById(id);
    }

    public User findUserByEmail(String email) {
        return userDBStore.findByEmail(email);
    }
}
