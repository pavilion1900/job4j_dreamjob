package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Application;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        User userInDb = store.findById(user.getId());
        assertThat(userInDb.getEmail()).isEqualTo(user.getEmail());
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
    public void whenFindByEmailUser() {
        User user = new User(0, "nick@abc.com", "1111");
        store.add(user);
        User userInDb = store.findByEmail("nick@abc.com");
        assertThat(userInDb.getEmail()).isEqualTo(user.getEmail());
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
