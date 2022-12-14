package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Application;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserDBStoreTest {

    private static final String DELETE_USERS = "DELETE FROM users";
    private final UserDBStore store = new UserDBStore(new Application().loadPool());

    @BeforeEach
    void wipeTable() {
        try (Connection cn = new Application().loadPool().getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE_USERS)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddUserWithUniqueEmail() {
        User user = new User(0, "nick@abc.com", "1111");
        store.add(user);
        Optional<User> userInDb = store.findById(user.getId());
        assertThat(userInDb.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenAddUserWithNotUniqueEmail() {
        User firstUser = new User(0, "nick@abc.com", "1111");
        User secondUser = new User(0, "nick@abc.com", "2222");
        store.add(firstUser);
        store.add(secondUser);
        List<User> users = store.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void whenFindUserByEmail() {
        User user = new User(0, "nick@abc.com", "1111");
        store.add(user);
        Optional<User> userInDb = store.findByEmail("nick@abc.com");
        assertThat(userInDb.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenFindUserByEmailAndPasswordWithEqualsPassword() {
        User user = new User(0, "nick@abc.com", "1111");
        store.add(user);
        Optional<User> userInDb = store.findByEmailAndPassword("nick@abc.com", "1111");
        assertAll(
                () -> assertThat(userInDb.get().getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(userInDb.get().getPassword()).isEqualTo(user.getPassword())
        );
    }

    @Test
    public void whenFindUserByEmailAndPasswordWithDifferentPassword() {
        User user = new User(0, "nick@abc.com", "1111");
        store.add(user);
        Optional<User> userInDb = store.findByEmailAndPassword("nick@abc.com", "2222");
        assertThat(userInDb).isEqualTo(Optional.empty());
    }

    @Test
    public void whenFindAllUsers() {
        User firstUser = new User(0, "nick@abc.com", "1111");
        User secondUser = new User(0, "oleg@abc.com", "2222");
        store.add(firstUser);
        store.add(secondUser);
        List<User> users = store.findAll();
        assertThat(users.size()).isEqualTo(2);
    }
}
